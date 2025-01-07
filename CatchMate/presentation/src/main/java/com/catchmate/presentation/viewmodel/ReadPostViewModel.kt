package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.DeleteBoardResponse
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.enroll.PostEnrollRequest
import com.catchmate.domain.model.enroll.PostEnrollResponse
import com.catchmate.domain.model.enumclass.EnrollState
import com.catchmate.domain.usecase.board.DeleteBoardUseCase
import com.catchmate.domain.usecase.board.GetBoardUseCase
import com.catchmate.domain.usecase.board.PostBoardLikeUseCase
import com.catchmate.domain.usecase.enroll.PostEnrollUseCase
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
        private val postEnrollUseCase: PostEnrollUseCase,
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

        private val _postEnrollResponse = MutableLiveData<PostEnrollResponse>()
        val postEnrollResponse: LiveData<PostEnrollResponse>
            get() = _postEnrollResponse

        private val _deleteBoardResponse = MutableLiveData<DeleteBoardResponse>()
        val deleteBoardResponse: LiveData<DeleteBoardResponse>
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
            postEnrollRequest: PostEnrollRequest,
        ) {
            viewModelScope.launch {
                val result = postEnrollUseCase.postEnroll(boardId, postEnrollRequest)
                result
                    .onSuccess { response ->
                        _postEnrollResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun setBoardEnrollState(state: EnrollState) {
            _boardEnrollState.value = state
        }

        fun deleteBoard(boardId: Long) {
            viewModelScope.launch {
                val result = deleteBoardUseCase.deleteBoard(boardId)
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
