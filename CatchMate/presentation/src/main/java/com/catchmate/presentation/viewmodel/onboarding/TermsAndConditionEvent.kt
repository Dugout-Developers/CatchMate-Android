package com.catchmate.presentation.viewmodel.onboarding

sealed interface TermsAndConditionEvent {
    data object OnBackClicked : TermsAndConditionEvent
    data object OnSubmitClicked : TermsAndConditionEvent
    data object OnAllAgreementToggled : TermsAndConditionEvent
    data object OnServiceTermsToggled : TermsAndConditionEvent
    data object OnPrivacyPolicyToggled : TermsAndConditionEvent
    data object OnMarketingPushToggled : TermsAndConditionEvent
    data object OnServiceDetailClicked : TermsAndConditionEvent
    data object OnPrivacyDetailClicked : TermsAndConditionEvent
    data object OnMarketingDetailClicked : TermsAndConditionEvent
}
