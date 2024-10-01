package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.BoardReadResponse
import com.catchmate.domain.usecase.BoardLikeUseCase
import com.catchmate.domain.usecase.BoardReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadPostViewModel
    @Inject
    constructor(
        private val boardReadUseCase: BoardReadUseCase,
        private val boardLikeUseCase: BoardLikeUseCase,
    ) : ViewModel() {
        private var _boardReadResponse = MutableLiveData<BoardReadResponse>()
        val boardReadResponse: LiveData<BoardReadResponse>
            get() = _boardReadResponse

        private var _boardLikeResponse = MutableLiveData<Int>()
        val boardLikeResponse: LiveData<Int>
            get() = _boardLikeResponse

        fun getBoard(
            boardId: Long,
        ) {
            viewModelScope.launch {
                _boardReadResponse.value = boardReadUseCase.getBoard(boardId)
            }
        }

        fun postBoardLike(
            boardId: Long,
            flag: Int,
        ) {
            viewModelScope.launch {
                _boardLikeResponse.value = boardLikeUseCase.postBoardLike(boardId, flag)
            }
        }
    }
