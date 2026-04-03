package com.catchmate.presentation.viewmodel.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.usecase.notification.DeleteNotificationUseCase
import com.catchmate.domain.usecase.notification.GetNotificationListUseCase
import com.catchmate.domain.usecase.notification.GetReceivedNotificationUseCase
import com.catchmate.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel
    @Inject
    constructor(
        private val getNotificationListUseCase: GetNotificationListUseCase,
        private val getReceivedNotificationUseCase: GetReceivedNotificationUseCase,
        private val deleteNotificationUseCase: DeleteNotificationUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(NotificationUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<NotificationSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        private fun sendSideEffect(effect: NotificationSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }

        fun onEvent(event: NotificationEvent) {
            when (event) {
                NotificationEvent.OnBackClicked -> {
                    sendSideEffect(NotificationSideEffect.NavigateToBack)
                }

                is NotificationEvent.OnItemClicked -> {
                    getReceivedNotification(event.id)
                }

                is NotificationEvent.OnItemSwiped -> {
                    deleteNotification(event.id)
                }

                NotificationEvent.InitData -> {
                    getNotificationList()
                }

                NotificationEvent.OnMoreListLoaded -> {
                    getNotificationList(
                        page = uiState.value.pageNumber + 1,
                        isReadMore = true,
                    )
                }
            }
        }

        fun getNotificationList(
            page: Int = 0,
            size: Int = 10,
            isReadMore: Boolean = false,
        ) {
            viewModelScope.launch {
                val result = getNotificationListUseCase(page, size)
                result
                    .onSuccess { response ->
                        _uiState.update {
                            if (isReadMore) {
                                it.copy(
                                    notificationList = it.notificationList?.plus(response.content),
                                    pageNumber = response.pageNumber,
                                    hasNext = response.hasNext,
                                )
                            } else {
                                it.copy(
                                    notificationList = response.content,
                                    pageNumber = response.pageNumber,
                                    hasNext = response.hasNext,
                                )
                            }
                        }
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            sendSideEffect(NotificationSideEffect.NavigateToLogin)
                        } else {
                            _uiState.update {
                                it.copy(
                                    notificationList = null,
                                )
                            }
                        }
                    }
            }
        }

        fun getReceivedNotification(notificationId: Long) {
            viewModelScope.launch {
                val result = getReceivedNotificationUseCase.getReceivedNotification(notificationId)
                result
                    .onSuccess { response ->
                        sendSideEffect(NotificationSideEffect.NavigateToDetail)
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            sendSideEffect(NotificationSideEffect.NavigateToLogin)
                        } else {
                            sendSideEffect(NotificationSideEffect.ShowSnackBar(R.string.all_component_error_msg))
                        }
                    }
            }
        }

        fun deleteNotification(notificationId: Long) {
            viewModelScope.launch {
                val result = deleteNotificationUseCase(notificationId)
                result
                    .onSuccess { response ->
                        val newList = uiState.value.notificationList?.filter { it.id != notificationId }
                        _uiState.update {
                            it.copy(
                                notificationList = newList,
                            )
                        }
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            sendSideEffect(NotificationSideEffect.NavigateToLogin)
                        } else {
                            sendSideEffect(NotificationSideEffect.ShowSnackBar(R.string.notification_delete_error_snackbar))
                        }
                    }
            }
        }
    }
