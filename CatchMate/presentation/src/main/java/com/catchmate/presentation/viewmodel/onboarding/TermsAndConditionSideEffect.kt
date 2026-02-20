package com.catchmate.presentation.viewmodel.onboarding

sealed interface TermsAndConditionSideEffect {
    data object NavigateBack : TermsAndConditionSideEffect
    data object NavigateToNext : TermsAndConditionSideEffect
    data class NavigateToWeb(val url: String) : TermsAndConditionSideEffect
    data class ShowError(val message: String) : TermsAndConditionSideEffect
}
