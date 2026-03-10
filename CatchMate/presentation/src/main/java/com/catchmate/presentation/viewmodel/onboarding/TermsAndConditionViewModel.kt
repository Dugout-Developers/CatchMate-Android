package com.catchmate.presentation.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(TermsAndConditionUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<TermsAndConditionSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        fun onEvent(event: TermsAndConditionEvent) {
            when (event) {
                TermsAndConditionEvent.OnBackClicked -> {
                    sendSideEffect(TermsAndConditionSideEffect.NavigateBack)
                }

                TermsAndConditionEvent.OnSubmitClicked -> {
                    if (uiState.value.isSubmitButtonEnable) {
                        sendSideEffect(TermsAndConditionSideEffect.NavigateToNext(uiState.value.isMarketingPushChecked))
                    }
                }

                TermsAndConditionEvent.OnServiceDetailClicked -> {
                    sendSideEffect(
                        TermsAndConditionSideEffect
                            .NavigateToWeb("https://catchmate.notion.site/19690504ec15803588a7ca69b306bf3e"),
                    )
                }

                TermsAndConditionEvent.OnPrivacyDetailClicked -> {
                    sendSideEffect(
                        TermsAndConditionSideEffect
                            .NavigateToWeb("https://catchmate.notion.site/19690504ec15804ba163fcf8fa0ab937"),
                    )
                }

                TermsAndConditionEvent.OnMarketingDetailClicked -> {
                    sendSideEffect(
                        TermsAndConditionSideEffect
                            .NavigateToWeb("https://catchmate.notion.site/1b890504ec15805fa95ef55c252d53e6"),
                    )
                }

                TermsAndConditionEvent.OnAllAgreementToggled -> {
                    toggleAllAgreement()
                }

                TermsAndConditionEvent.OnServiceTermsToggled -> {
                    updateCheckState {
                        it.copy(isServiceTermsChecked = !it.isServiceTermsChecked)
                    }
                }

                TermsAndConditionEvent.OnPrivacyPolicyToggled -> {
                    updateCheckState {
                        it.copy(isPrivacyPolicyChecked = !it.isPrivacyPolicyChecked)
                    }
                }

                TermsAndConditionEvent.OnMarketingPushToggled -> {
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
                    isSubmitButtonEnable = newValue,
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
                    isSubmitButtonEnable = isNextButtonEnabled,
                )
            }
        }
    }
