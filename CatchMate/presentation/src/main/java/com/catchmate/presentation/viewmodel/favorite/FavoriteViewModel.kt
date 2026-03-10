package com.catchmate.presentation.viewmodel.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.usecase.board.GetLikedBoardUseCase
import com.catchmate.domain.usecase.board.PostBoardLikeUseCase
import com.catchmate.presentation.viewmodel.favorite.FavoriteSideEffect.NavigateToLogin
import com.catchmate.presentation.viewmodel.favorite.FavoriteSideEffect.NavigateToReadPost
import com.catchmate.presentation.viewmodel.favorite.FavoriteSideEffect.ShowSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        private val getLikedBoardUseCase: GetLikedBoardUseCase,
        private val postBoardLikeUseCase: PostBoardLikeUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FavoriteUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<FavoriteSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        private fun sendSideEffect(effect: FavoriteSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }

        fun onEvent(event: FavoriteEvent) {
            when (event) {
                FavoriteEvent.InitData -> {
                    getLikedBoard()
                }

                is FavoriteEvent.OnFavoriteBoardClicked -> {
                    sendSideEffect(NavigateToReadPost(event.boardId))
                }

                is FavoriteEvent.OnFavoriteBoardUnliked -> {
                    postBoardLike(event.boardId)
                }

                FavoriteEvent.OnLoadMoreBoards -> {
                    getLikedBoard(
                        page = uiState.value.pageNumber + 1,
                        isReadMore = true,
                    )
                }
            }
        }

        fun postBoardLike(boardId: Long) {
            viewModelScope.launch {
                val result = postBoardLikeUseCase.postBoardLike(boardId)
                result
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                boardList = it.boardList?.filter { it.boardId != response.boardId },
                            )
                        }
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            sendSideEffect(NavigateToLogin)
                        } else {
                            sendSideEffect(ShowSnackBar)
                        }
                    }
            }
        }

        fun getLikedBoard(
            page: Int = 0,
            size: Int = 10,
            isReadMore: Boolean = false,
        ) {
            viewModelScope.launch {
                val result = getLikedBoardUseCase.getLikedBoard(page, size)
                result
                    .onSuccess { response ->
                        _uiState.update {
                            if (isReadMore) {
                                it.copy(
                                    boardList = it.boardList?.plus(response.content),
                                    pageNumber = response.pageNumber,
                                    hasNext = response.hasNext,
                                )
                            } else {
                                it.copy(
                                    boardList = response.content,
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
