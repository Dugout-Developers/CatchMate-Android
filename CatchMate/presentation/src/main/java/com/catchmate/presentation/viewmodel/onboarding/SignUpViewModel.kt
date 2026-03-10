package com.catchmate.presentation.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.usecase.user.GetCheckNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val getCheckNicknameUseCase: GetCheckNicknameUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SignUpUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<SignUpSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        private var nicknameCheckJob: Job? = null

        fun onEvent(event: SignUpEvent) {
            when (event) {
                SignUpEvent.OnBackClicked -> {
                    sendSideEffect(SignUpSideEffect.NavigateToBack)
                }

                is SignUpEvent.OnSubmitClicked -> {
                    if (uiState.value.isSubmitButtonEnable) {
                        sendSideEffect(
                            SignUpSideEffect.NavigateToNext(
                                nickname = uiState.value.nickname,
                                birthDate = uiState.value.birthDate,
                                gender = uiState.value.gender,
                            ),
                        )
                    }
                }

                SignUpEvent.OnNicknameClearClicked -> {
                    _uiState.update { it.copy(nickname = "", isNicknameValid = false) }
                }

                is SignUpEvent.OnBirthDateChanged -> {
                    _uiState.update { it.copy(birthDate = event.birthDate) }
                    validateAll()
                }

                is SignUpEvent.OnGenderSelected -> {
                    _uiState.update { it.copy(gender = event.gender) }
                }

                is SignUpEvent.OnNicknameChanged -> {
                    val limitedNickname =
                        if (event.nickname.length > 10) {
                            event.nickname.take(10)
                        } else {
                            event.nickname
                        }
                    _uiState.update { it.copy(nickname = limitedNickname) }
                    searchNicknameWithDebounce(limitedNickname)
                }
            }
        }

        private fun validateAll() {
            _uiState.update {
                it.copy(
                    isSubmitButtonEnable =
                        uiState.value.nickname.isNotEmpty() &&
                            uiState.value.birthDate.isNotEmpty() &&
                            uiState.value.isNicknameValid,
                )
            }
        }

        private fun sendSideEffect(effect: SignUpSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }

        private fun searchNicknameWithDebounce(nickName: String) {
            nicknameCheckJob?.cancel() // 이전 작업 취소

            if (nickName.isBlank()) {
                _uiState.update { it.copy(isNicknameValid = false) }
                validateAll()
                return
            }

            nicknameCheckJob =
                viewModelScope.launch {
                    delay(500L)
                    getCheckNickname(nickName)
                }
        }

        fun getCheckNickname(nickName: String) {
            viewModelScope.launch {
                val result = getCheckNicknameUseCase(nickName)
                result
                    .onSuccess { response ->
                        _uiState.update { it.copy(isNicknameValid = response.available) }
                        validateAll()
                    }.onFailure { exception ->
                        sendSideEffect(SignUpSideEffect.ShowSnackBar(exception.message.toString()))
                    }
            }
        }
    }
