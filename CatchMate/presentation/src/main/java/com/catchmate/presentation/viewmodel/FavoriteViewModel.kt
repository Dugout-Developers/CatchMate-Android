package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.GetLikedBoardResponse
import com.catchmate.domain.usecase.board.GetLikedBoardUseCase
import com.catchmate.domain.usecase.board.PostBoardLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        private val getLikedBoardUseCase: GetLikedBoardUseCase,
        private val postBoardLikeUseCase: PostBoardLikeUseCase,
    ) : ViewModel() {
        private val _getLikedBoardResponse = MutableLiveData<GetLikedBoardResponse>()
        val getLikedBoardResponse: LiveData<GetLikedBoardResponse>
            get() = _getLikedBoardResponse

        private val _errorMessage = MutableLiveData<String>()
        val errorMessage: LiveData<String>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        private val _postBoardLikeResponse = MutableLiveData<Unit>()
        val postBoardLikeResponse: LiveData<Unit>
            get() = _postBoardLikeResponse

        fun postBoardLike(boardId: Long) {
            viewModelScope.launch {
                val result = postBoardLikeUseCase.postBoardLike(boardId)
                result
                    .onSuccess { response ->
                        _postBoardLikeResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun getLikedBoard(page: Int, size: Int = 10) {
            viewModelScope.launch {
                val result = getLikedBoardUseCase.getLikedBoard(page, size)
                result
                    .onSuccess { likedBoards ->
                        _getLikedBoardResponse.value = likedBoards
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = "ListLoadError"
                        }
                    }
            }
        }
    }
