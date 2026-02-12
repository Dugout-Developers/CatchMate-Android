package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.notification.GetHasUnreadNotificationResponse
import com.catchmate.domain.usecase.notification.GetHasUnreadNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val getHasUnreadNotificationUseCase: GetHasUnreadNotificationUseCase,
    ) : ViewModel() {
        private val _getHasUnreadNotificationResponse = MutableLiveData<GetHasUnreadNotificationResponse>()
        val getHasUnreadNotificationResponse: LiveData<GetHasUnreadNotificationResponse>
            get() = _getHasUnreadNotificationResponse

        private val _isGuestLogin = MutableLiveData<Boolean>()
        val isGuestLogin: LiveData<Boolean> get() = _isGuestLogin

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        fun setGuestLogin(isGuest: Boolean) {
            _isGuestLogin.value = isGuest
        }

        fun getHasUnreadNotification() {
            viewModelScope.launch {
                val result = getHasUnreadNotificationUseCase()
                result
                    .onSuccess { response ->
                        _getHasUnreadNotificationResponse.value = response
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
