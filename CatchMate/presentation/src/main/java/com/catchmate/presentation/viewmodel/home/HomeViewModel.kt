package com.catchmate.presentation.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.GetBoardListResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.domain.usecase.board.GetBoardListUseCase
import com.catchmate.domain.usecase.user.GetUserProfileUseCase
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getBoardListUseCase: GetBoardListUseCase,
    ) : ViewModel() {

        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<HomeSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        private fun sendSideEffect(effect: HomeSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }

        fun onEvent(event: HomeEvent) {
            when (event) {
                HomeEvent.InitData -> {
                    getBoardList()
                }
                HomeEvent.OnNotificationClicked -> {
                    sendSideEffect(NavigateToNotification)
                }
                is HomeEvent.OnDateFilterClicked -> {
                    // boardlist 비우고 getBoardList 다시 호출
                    sendSideEffect(ShowDatePickerBottomSheet)
                }
                HomeEvent.OnClubFilterClicked -> {
                    // boardlist 비우고 getBoardList 다시 호출
                    sendSideEffect(ShowClubBottomSheet)
                }
                HomeEvent.OnMemberFilterClicked -> {
                    // boardlist 비우고 getBoardList 다시 호출
                    sendSideEffect(ShowMemberCountBottomSheet)
                }
                is HomeEvent.OnBoardItemClicked -> {
                    sendSideEffect(NavigateToReadPost(event.boardId))
                }
                HomeEvent.OnLoadMoreBoards -> {
                    getBoardList(
                        gameDate = uiState.value.dateFilterData,
                        maxPerson = uiState.value.memberFilterData.toInt(),
                    )
                }
                is HomeEvent.OnBoardDeleted -> {
                    removeBoardItem(event.boardId)
                }
            }
        }

        private fun removeBoardItem(deletedBoardId: Long) {
            _uiState.update { currentState ->
                val updatedList = currentState.boardList.filter { it.boardId != deletedBoardId }
                currentState.copy(boardList = updatedList)
            }
        }

        private fun getBoardList(
            gameDate: String? = null,
            maxPerson: Int? = null,
            preferredTeamIdList: Array<Int>? = null,
            page: Int = 0,
            size: Int = 10,
        ) {
            viewModelScope.launch {
                val result = getBoardListUseCase.getBoardList(gameDate, maxPerson, preferredTeamIdList, page, size)
                result
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                boardList = it.boardList + response.content,
                                pageNumber = response.pageNumber,
                                hasNext = response.hasNext,
                            )
                        }
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            sendSideEffect(NavigateToLogin)
                        } else {
                            // 빈 리스트 화면에 문제 발생 화면 표시 로직 구현
                        }
                    }
            }
        }
    }