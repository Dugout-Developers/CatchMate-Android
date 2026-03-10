package com.catchmate.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.usecase.board.GetBoardListUseCase
import com.catchmate.presentation.view.components.FilterSheetType
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.NavigateToLogin
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.NavigateToNotification
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.NavigateToReadPost
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.ShowClubBottomSheet
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.ShowDatePickerBottomSheet
import com.catchmate.presentation.viewmodel.home.HomeSideEffect.ShowMemberCountBottomSheet
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
                    sendSideEffect(ShowDatePickerBottomSheet)
                }

                HomeEvent.OnClubFilterClicked -> {
                    sendSideEffect(ShowClubBottomSheet)
                }

                HomeEvent.OnMemberFilterClicked -> {
                    sendSideEffect(ShowMemberCountBottomSheet)
                }

                is HomeEvent.OnBoardItemClicked -> {
                    sendSideEffect(NavigateToReadPost(event.boardId))
                }

                HomeEvent.OnLoadMoreBoards -> {
                    val gameDate =
                        if (uiState.value.selectedDate == null) {
                            null
                        } else {
                            uiState.value.selectedDate.toString()
                        }
                    val maxPerson =
                        if (uiState.value.memberFilterData.isBlank()) {
                            null
                        } else {
                            uiState.value.memberFilterData.toInt()
                        }
                    val preferredTeamIdList =
                        if (uiState.value.clubFilterData.isEmpty()) {
                            null
                        } else {
                            uiState.value.clubFilterData.toTypedArray()
                        }
                    getBoardList(
                        gameDate = gameDate,
                        maxPerson = maxPerson,
                        preferredTeamIdList = preferredTeamIdList,
                        page = uiState.value.pageNumber + 1,
                        isReadMore = true,
                    )
                }

                is HomeEvent.OnBoardDeleted -> {
                    removeBoardItem(event.boardId)
                }

                is HomeEvent.OnFilterApplied -> {
                    _uiState.update { state ->
                        when (event.sheetType) {
                            is FilterSheetType.Club -> {
                                state.copy(
                                    clubFilterData = event.sheetType.initialClubIds,
                                )
                            }

                            is FilterSheetType.Date -> {
                                state.copy(
                                    selectedDate = event.sheetType.initialDate,
                                )
                            }

                            is FilterSheetType.Member -> {
                                state.copy(
                                    memberFilterData = event.sheetType.initialCount,
                                )
                            }
                        }
                    }
                    val gameDate =
                        if (uiState.value.selectedDate == null) {
                            null
                        } else {
                            uiState.value.selectedDate.toString()
                        }
                    val maxPerson =
                        if (uiState.value.memberFilterData.isBlank()) {
                            null
                        } else {
                            uiState.value.memberFilterData.toInt()
                        }
                    val preferredTeamIdList =
                        if (uiState.value.clubFilterData.isEmpty()) {
                            null
                        } else {
                            uiState.value.clubFilterData.toTypedArray()
                        }

                    getBoardList(
                        gameDate = gameDate,
                        maxPerson = maxPerson,
                        preferredTeamIdList = preferredTeamIdList,
                    )
                }

                is HomeEvent.OnFilterReset -> {
                    when (event.filterSheetType) {
                        is FilterSheetType.Club -> _uiState.update { it.copy(clubFilterData = emptyList()) }
                        is FilterSheetType.Date -> _uiState.update { it.copy(selectedDate = null) }
                        is FilterSheetType.Member -> _uiState.update { it.copy(memberFilterData = "") }
                    }
                    val gameDate =
                        if (uiState.value.selectedDate == null) {
                            null
                        } else {
                            uiState.value.selectedDate.toString()
                        }
                    val maxPerson =
                        if (uiState.value.memberFilterData.isBlank()) {
                            null
                        } else {
                            uiState.value.memberFilterData.toInt()
                        }
                    val preferredTeamIdList =
                        if (uiState.value.clubFilterData.isEmpty()) {
                            null
                        } else {
                            uiState.value.clubFilterData.toTypedArray()
                        }
                    getBoardList(
                        gameDate = gameDate,
                        maxPerson = maxPerson,
                        preferredTeamIdList = preferredTeamIdList,
                    )
                }
            }
        }

        private fun removeBoardItem(deletedBoardId: Long) {
            _uiState.update { currentState ->
                val updatedList = currentState.boardList?.filter { it.boardId != deletedBoardId }
                currentState.copy(boardList = updatedList)
            }
        }

        private fun getBoardList(
            gameDate: String? = null,
            maxPerson: Int? = null,
            preferredTeamIdList: Array<Int>? = null,
            page: Int = 0,
            size: Int = 10,
            isReadMore: Boolean = false,
        ) {
            viewModelScope.launch {
                val result = getBoardListUseCase.getBoardList(gameDate, maxPerson, preferredTeamIdList, page, size)
                result
                    .onSuccess { response ->
                        _uiState.update {
                            if (isReadMore) {
                                it.copy(
                                    boardList = it.boardList?.plus(response.content), // next page 가져오는 경우의 api 호출에만 기존 리스트에 추가되도록.
                                    pageNumber = response.pageNumber,
                                    hasNext = response.hasNext,
                                )
                            } else {
                                it.copy(
                                    boardList = response.content, // 최초 호출 또는 바텀시트 필터 선택 후 api 호출 시
                                    pageNumber = response.pageNumber,
                                    hasNext = response.hasNext,
                                )
                            }
                        }
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            sendSideEffect(NavigateToLogin)
                        } else {
                            _uiState.update {
                                it.copy(
                                    boardList = null,
                                )
                            }
                        }
                    }
            }
        }
    }
