package com.catchmate.presentation.viewmodel.onboarding

interface CheerStyleOnboardingSideEffect {
    object NavigateToBack : CheerStyleOnboardingSideEffect

    object NavigateToNext : CheerStyleOnboardingSideEffect

    data class ShowSnackBar(
        val message: String,
    ) : CheerStyleOnboardingSideEffect
}
