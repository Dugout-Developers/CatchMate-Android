package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.BoardReadResponse
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollResponse
import com.catchmate.domain.model.EnrollState
import com.catchmate.domain.usecase.BoardLikeUseCase
import com.catchmate.domain.usecase.BoardReadUseCase
import com.catchmate.domain.usecase.EnrollUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadPostViewModel
    @Inject
    constructor(
        private val boardReadUseCase: BoardReadUseCase,
        private val boardLikeUseCase: BoardLikeUseCase,
        private val enrollUseCase: EnrollUseCase,
    ) : ViewModel() {
        private val _boardReadResponse = MutableLiveData<BoardReadResponse>()
        val boardReadResponse: LiveData<BoardReadResponse>
            get() = _boardReadResponse

        private val _boardLikeResponse = MutableLiveData<Int>()
        val boardLikeResponse: LiveData<Int>
            get() = _boardLikeResponse

        private val _boardEnrollState = MutableLiveData<EnrollState>()
        val boardEnrollState: LiveData<EnrollState>
            get() = _boardEnrollState

        private val _enrollResponse = MutableLiveData<EnrollResponse>()
        val enrollResponse: LiveData<EnrollResponse>
            get() = _enrollResponse

        fun getBoard(boardId: Long) {
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

        fun postEnroll(
            boardId: Long,
            enrollRequest: EnrollRequest,
        ) {
            viewModelScope.launch {
                _enrollResponse.value = enrollUseCase.postEnroll(boardId, enrollRequest)
            }
        }

        fun setBoardEnrollState(state: EnrollState) {
            _boardEnrollState.value = state
        }
    }
