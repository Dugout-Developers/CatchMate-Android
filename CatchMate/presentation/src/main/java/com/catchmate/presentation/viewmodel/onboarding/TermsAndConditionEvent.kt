package com.catchmate.presentation.viewmodel.onboarding

sealed interface TermsAndConditionEvent {
    data object OnClickBack : TermsAndConditionEvent
    data object OnClickNext : TermsAndConditionEvent
    data object OnToggleAllAgreement : TermsAndConditionEvent
    data object OnToggleServiceTerms : TermsAndConditionEvent
    data object OnTogglePrivacyPolicy : TermsAndConditionEvent
    data object OnToggleMarketingPush : TermsAndConditionEvent
    data object OnClickServiceDetail : TermsAndConditionEvent
    data object OnClickPrivacyDetail : TermsAndConditionEvent
    data object OnClickMarketingDetail : TermsAndConditionEvent
}
