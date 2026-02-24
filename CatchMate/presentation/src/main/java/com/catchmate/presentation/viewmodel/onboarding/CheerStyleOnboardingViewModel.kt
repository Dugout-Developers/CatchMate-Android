package com.catchmate.presentation.viewmodel.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.usecase.user.PostUserAdditionalInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheerStyleOnboardingViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val postUserAdditionalInfoUseCase: PostUserAdditionalInfoUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CheerStyleOnboardingUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<CheerStyleOnboardingSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        private val initialUserInfo: PostUserAdditionalInfoRequest? =
            savedStateHandle.get<PostUserAdditionalInfoRequest>("userInfo")

        fun onEvent(event: CheerStyleOnboardingEvent) {
            when (event) {
                CheerStyleOnboardingEvent.OnBackClicked -> {
                    sendSideEffect(CheerStyleOnboardingSideEffect.NavigateToBack)
                }
                is CheerStyleOnboardingEvent.OnSubmitClicked -> {
                    try {
                        val request =
                            initialUserInfo?.copy(
                                watchStyle = uiState.value.cheerStyleButtonTextList[uiState.value.selectedButtonId!!].first.replace(" 스타일", "")
                            )
                        postUserAdditionalInfo(request!!)
                    } catch (_: Exception) {
                        postUserAdditionalInfo(initialUserInfo!!)
                    }
                }
                is CheerStyleOnboardingEvent.OnCheerStyleSelected -> {
                    _uiState.update {
                        it.copy(
                            selectedButtonId =
                                if (uiState.value.selectedButtonId == event.cheerStyleId) {
                                    -1
                                } else {
                                    event.cheerStyleId
                                },
                        )
                    }
                }
            }
        }

        fun setCheerStyleScreenData(
            imgList: List<Int>,
            textList: List<Pair<String, String>>,
        ) {
            _uiState.update {
                it.copy(
                    nickname = initialUserInfo?.nickName ?: "Unknown",
                    cheerStyleButtonLogoList = imgList,
                    cheerStyleButtonTextList = textList,
                )
            }
        }

        private fun sendSideEffect(effect: CheerStyleOnboardingSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }

        private fun postUserAdditionalInfo(request: PostUserAdditionalInfoRequest) {
            viewModelScope.launch {
                val result = postUserAdditionalInfoUseCase(request)
                result
                    .onSuccess {
                        sendSideEffect(CheerStyleOnboardingSideEffect.NavigateToNext)
                    }.onFailure { exception ->
                        sendSideEffect(CheerStyleOnboardingSideEffect.ShowSnackBar(exception.message.toString()))
                    }
            }
        }
    }
