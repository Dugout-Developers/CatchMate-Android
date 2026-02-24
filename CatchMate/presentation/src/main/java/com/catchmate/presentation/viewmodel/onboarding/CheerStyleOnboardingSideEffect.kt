package com.catchmate.presentation.viewmodel.onboarding

interface CheerStyleOnboardingSideEffect {
    data object NavigateToBack : CheerStyleOnboardingSideEffect
    data object NavigateToNext : CheerStyleOnboardingSideEffect
    data class ShowSnackBar(val message: String) : CheerStyleOnboardingSideEffect
}
