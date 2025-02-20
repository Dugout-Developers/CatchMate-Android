package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.chatting.GetChattingRoomListResponse
import com.catchmate.domain.usecase.chatting.GetChattingRoomListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingHomeViewModel
    @Inject
    constructor(
        private val getChattingRoomListUseCase: GetChattingRoomListUseCase,
    ) : ViewModel() {
        private val _getChattingRoomListResponse = MutableLiveData<GetChattingRoomListResponse>()
        val getChattingRoomListResponse: LiveData<GetChattingRoomListResponse>
            get() = _getChattingRoomListResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        fun getChattingRoomList(page: Int) {
            viewModelScope.launch {
                val result = getChattingRoomListUseCase.getChattingRoomList(page)
                result
                    .onSuccess { response ->
                        _getChattingRoomListResponse.value = response
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
