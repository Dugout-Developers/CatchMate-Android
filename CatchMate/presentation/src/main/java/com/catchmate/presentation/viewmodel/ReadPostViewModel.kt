package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.DeleteBoardRequest
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollResponse
import com.catchmate.domain.model.EnrollState
import com.catchmate.domain.model.GetBoardResponse
import com.catchmate.domain.usecase.EnrollUseCase
import com.catchmate.domain.usecase.board.DeleteBoardUseCase
import com.catchmate.domain.usecase.board.GetBoardUseCase
import com.catchmate.domain.usecase.board.PostBoardLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadPostViewModel
    @Inject
    constructor(
        private val getBoardUseCase: GetBoardUseCase,
        private val deleteBoardUseCase: DeleteBoardUseCase,
        private val postBoardLikeUseCase: PostBoardLikeUseCase,
        private val enrollUseCase: EnrollUseCase,
    ) : ViewModel() {
        private val _getBoardResponse = MutableLiveData<GetBoardResponse>()
        val getBoardResponse: LiveData<GetBoardResponse>
            get() = _getBoardResponse

        private val _postBoardLikeResponse = MutableLiveData<Int>()
        val postBoardLikeResponse: LiveData<Int>
            get() = _postBoardLikeResponse

        private val _boardEnrollState = MutableLiveData<EnrollState>()
        val boardEnrollState: LiveData<EnrollState>
            get() = _boardEnrollState

        private val _enrollResponse = MutableLiveData<EnrollResponse>()
        val enrollResponse: LiveData<EnrollResponse>
            get() = _enrollResponse

        private val _deleteBoardResponse = MutableLiveData<Int>()
        val deleteBoardResponse: LiveData<Int>
            get() = _deleteBoardResponse

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

        fun deleteBoard(deleteBoardRequest: DeleteBoardRequest) {
            viewModelScope.launch {
                val result = deleteBoardUseCase.deleteBoard(deleteBoardRequest)
                result
                    .onSuccess { response ->
                        _deleteBoardResponse.value = response
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
