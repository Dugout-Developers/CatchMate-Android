package com.catchmate.presentation.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.enumclass.AlarmType
import com.catchmate.domain.usecase.user.PatchUserAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsAndConditionViewModel
    @Inject
    constructor(
        private val patchUserAlarmUseCase: PatchUserAlarmUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(TermsAndConditionUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<TermsAndConditionSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        fun onEvent(event: TermsAndConditionEvent) {
            when (event) {
                TermsAndConditionEvent.OnClickBack -> sendSideEffect(TermsAndConditionSideEffect.NavigateBack)

                TermsAndConditionEvent.OnClickNext ->
                    if (uiState.value.isNextButtonEnable) {
                        patchUserAlarm()
                    }

                TermsAndConditionEvent.OnClickServiceDetail ->
                    sendSideEffect(
                        TermsAndConditionSideEffect
                            .NavigateToWeb("https://catchmate.notion.site/19690504ec15803588a7ca69b306bf3e")
                    )

                TermsAndConditionEvent.OnClickPrivacyDetail ->
                    sendSideEffect(
                        TermsAndConditionSideEffect
                            .NavigateToWeb("https://catchmate.notion.site/19690504ec15804ba163fcf8fa0ab937")
                    )

                TermsAndConditionEvent.OnClickMarketingDetail ->
                    sendSideEffect(
                        TermsAndConditionSideEffect
                            .NavigateToWeb("https://catchmate.notion.site/1b890504ec15805fa95ef55c252d53e6")
                    )

                TermsAndConditionEvent.OnToggleAllAgreement -> toggleAllAgreement()

                TermsAndConditionEvent.OnToggleServiceTerms -> {
                    updateCheckState {
                        it.copy(isServiceTermsChecked = !it.isServiceTermsChecked)
                    }
                }

                TermsAndConditionEvent.OnTogglePrivacyPolicy -> {
                    updateCheckState {
                        it.copy(isPrivacyPolicyChecked = !it.isPrivacyPolicyChecked)
                    }
                }

                TermsAndConditionEvent.OnToggleMarketingPush -> {
                    updateCheckState {
                        it.copy(isMarketingPushChecked = !it.isMarketingPushChecked)
                    }
                }
            }
        }

        private fun sendSideEffect(effect: TermsAndConditionSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }

        fun toggleAllAgreement() {
            _uiState.update { state ->
                val newValue = !state.isAllAgreementChecked
                state.copy(
                    isAllAgreementChecked = newValue,
                    isServiceTermsChecked = newValue,
                    isPrivacyPolicyChecked = newValue,
                    isMarketingPushChecked = newValue,
                    isNextButtonEnable = newValue,
                )
            }
        }

        private fun updateCheckState(reducer: (TermsAndConditionUiState) -> TermsAndConditionUiState) {
            _uiState.update { state ->
                val newState = reducer(state)

                val isAllChecked =
                    newState.isServiceTermsChecked &&
                            newState.isPrivacyPolicyChecked &&
                            newState.isMarketingPushChecked

                val isNextButtonEnabled =
                    newState.isServiceTermsChecked &&
                            newState.isPrivacyPolicyChecked

                newState.copy(
                    isAllAgreementChecked = isAllChecked,
                    isNextButtonEnable = isNextButtonEnabled
                )
            }
        }

        fun patchUserAlarm() {
            viewModelScope.launch {
                val result =
                    patchUserAlarmUseCase.patchUserAlarm(
                        AlarmType.ALL.name,
                        uiState.value.isAllAgreementChecked,
                    )
                result
                    .onSuccess { response ->
                        sendSideEffect(TermsAndConditionSideEffect.NavigateToNext)
                    }.onFailure { exception ->
                        sendSideEffect(TermsAndConditionSideEffect.ShowError(exception.message.toString()))
                    }
            }
        }
    }
