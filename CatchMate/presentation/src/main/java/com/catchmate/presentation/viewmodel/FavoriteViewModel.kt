package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.BoardListResponse
import com.catchmate.domain.usecase.BoardLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        private val boardLikeUseCase: BoardLikeUseCase,
    ) : ViewModel() {
        private val _boardListResponse = MutableLiveData<List<BoardListResponse>>()
        val boardListResponse: LiveData<List<BoardListResponse>>
            get() = _boardListResponse

        private val _errorMessage = MutableLiveData<String>()
        val errorMessage: LiveData<String>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        private val _boardLikeResponse = MutableLiveData<Int>()
        val boardLikeResponse: LiveData<Int>
            get() = _boardLikeResponse

        fun postBoardLike(
            boardId: Long,
            flag: Int,
        ) {
            viewModelScope.launch {
                _boardLikeResponse.value = boardLikeUseCase.postBoardLike(boardId, flag)
            }
        }

        fun getBoardLikedList() {
            viewModelScope.launch {
                val result = boardLikeUseCase.getBoardLikedList()

                result
                    .onSuccess { boardLikedList ->
                        _boardListResponse.value = boardLikedList
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
