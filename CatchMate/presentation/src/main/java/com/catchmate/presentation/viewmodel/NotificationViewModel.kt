package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetReceivedNotificationListResponse
import com.catchmate.domain.usecase.notification.GetReceivedNotificationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel
    @Inject
    constructor(
        private val getReceivedNotificationListUseCase: GetReceivedNotificationListUseCase,
    ) : ViewModel() {
        private val _receivedNotificationList = MutableLiveData<GetReceivedNotificationListResponse>()
        val receivedNotificationList: LiveData<GetReceivedNotificationListResponse>
            get() = _receivedNotificationList

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        fun getReceivedNotificationList() {
            viewModelScope.launch {
                val result = getReceivedNotificationListUseCase.getReceivedNotificationList()
                result
                    .onSuccess { response ->
                        _receivedNotificationList.value = response
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
