package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.GetUserBoardListResponse
import com.catchmate.domain.usecase.board.GetUserBoardListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel
    @Inject
    constructor(
        private val getUserBoardListUseCase: GetUserBoardListUseCase,
    ) : ViewModel() {
        private val _getUserBoardListResponse = MutableLiveData<GetUserBoardListResponse>()
        val getUserBoardListResponse: LiveData<GetUserBoardListResponse>
            get() = _getUserBoardListResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        fun getUserBoardList(
            userId: Long,
            page: Int,
        ) {
            viewModelScope.launch {
                val result = getUserBoardListUseCase.getUserBoardList(userId, page)
                result
                    .onSuccess { response ->
                        _getUserBoardListResponse.value = response
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
