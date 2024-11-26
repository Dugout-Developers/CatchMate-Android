package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.BoardDeleteRequest
import com.catchmate.domain.model.GetBoardResponse
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollResponse
import com.catchmate.domain.model.EnrollState
import com.catchmate.domain.usecase.BoardLikeUseCase
import com.catchmate.domain.usecase.BoardReadUseCase
import com.catchmate.domain.usecase.EnrollUseCase
import com.catchmate.domain.usecase.board.GetBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadPostViewModel
    @Inject
    constructor(
        private val getBoardUseCase: GetBoardUseCase,
        private val boardReadUseCase: BoardReadUseCase,
        private val boardLikeUseCase: BoardLikeUseCase,
        private val enrollUseCase: EnrollUseCase,
    ) : ViewModel() {
        private val _getBoardResponse = MutableLiveData<GetBoardResponse>()
        val getBoardResponse: LiveData<GetBoardResponse>
            get() = _getBoardResponse

        private val _boardLikeResponse = MutableLiveData<Int>()
        val boardLikeResponse: LiveData<Int>
            get() = _boardLikeResponse

        private val _boardEnrollState = MutableLiveData<EnrollState>()
        val boardEnrollState: LiveData<EnrollState>
            get() = _boardEnrollState

        private val _enrollResponse = MutableLiveData<EnrollResponse>()
        val enrollResponse: LiveData<EnrollResponse>
            get() = _enrollResponse

        private val _boardDeleteResponse = MutableLiveData<Int>()
        val boardDeleteResponse: LiveData<Int>
            get() = _boardDeleteResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        fun getBoard(boardId: Long) {
            viewModelScope.launch {
                val result = getBoardUseCase.getBoard(boardId)
                result
                    .onSuccess { response ->
                        _getBoardResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
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

        fun deleteBoard(boardDeleteResponse: BoardDeleteRequest) {
            viewModelScope.launch {
                _boardDeleteResponse.value = boardReadUseCase.deleteBoard(boardDeleteResponse)
            }
        }
    }
