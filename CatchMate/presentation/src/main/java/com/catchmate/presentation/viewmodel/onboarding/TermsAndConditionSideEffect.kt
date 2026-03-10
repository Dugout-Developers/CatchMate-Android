package com.catchmate.presentation.viewmodel.onboarding

sealed interface TermsAndConditionSideEffect {
    object NavigateBack : TermsAndConditionSideEffect

    data class NavigateToNext(
        val isMarketingPushChecked: Boolean,
    ) : TermsAndConditionSideEffect

    data class NavigateToWeb(
        val url: String,
    ) : TermsAndConditionSideEffect

    data class ShowError(
        val message: String,
    ) : TermsAndConditionSideEffect
}
