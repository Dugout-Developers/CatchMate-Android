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
        private val postBoardLikeUseCase: PostBoardLikeUseCase,
        private val getLikedBoardUseCase: GetLikedBoardUseCase,
    ) : ViewModel() {
        private val _getLikedBoardResponse = MutableLiveData<List<GetLikedBoardResponse>>()
        val getLikedBoardResponse: LiveData<List<GetLikedBoardResponse>>
            get() = _getLikedBoardResponse

        private val _errorMessage = MutableLiveData<String>()
        val errorMessage: LiveData<String>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        private val _postBoardLikeResponse = MutableLiveData<Int>()
        val postBoardLikeResponse: LiveData<Int>
            get() = _postBoardLikeResponse

        fun postBoardLike(
            boardId: Long,
            flag: Int,
        ) {
            viewModelScope.launch {
                val result = postBoardLikeUseCase.postBoardLike(boardId, flag)
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

        fun getLikedBoard() {
            viewModelScope.launch {
                val result = getLikedBoardUseCase.getLikedBoard()
                result
                    .onSuccess { likedBoards ->
                        _getLikedBoardResponse.value = likedBoards
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }
    }
