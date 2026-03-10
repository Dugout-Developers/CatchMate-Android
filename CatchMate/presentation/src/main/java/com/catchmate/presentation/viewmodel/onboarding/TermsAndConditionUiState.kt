package com.catchmate.presentation.viewmodel.onboarding

data class TermsAndConditionUiState(
    val isSubmitButtonEnable: Boolean = false,
    val isAllAgreementChecked: Boolean = false,
    val isServiceTermsChecked: Boolean = false,
    val isPrivacyPolicyChecked: Boolean = false,
    val isMarketingPushChecked: Boolean = false,
)
