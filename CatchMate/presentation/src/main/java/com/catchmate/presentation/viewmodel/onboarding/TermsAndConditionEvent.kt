package com.catchmate.presentation.viewmodel.onboarding

sealed interface TermsAndConditionEvent {
    object OnBackClicked : TermsAndConditionEvent

    object OnSubmitClicked : TermsAndConditionEvent

    object OnAllAgreementToggled : TermsAndConditionEvent

    object OnServiceTermsToggled : TermsAndConditionEvent

    object OnPrivacyPolicyToggled : TermsAndConditionEvent

    object OnMarketingPushToggled : TermsAndConditionEvent

    object OnServiceDetailClicked : TermsAndConditionEvent

    object OnPrivacyDetailClicked : TermsAndConditionEvent

    object OnMarketingDetailClicked : TermsAndConditionEvent
}
