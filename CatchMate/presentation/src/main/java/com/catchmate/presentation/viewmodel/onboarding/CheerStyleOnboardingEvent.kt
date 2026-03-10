package com.catchmate.presentation.viewmodel.onboarding

interface CheerStyleOnboardingEvent {
    object OnBackClicked : CheerStyleOnboardingEvent

    object OnSubmitClicked : CheerStyleOnboardingEvent

    data class OnCheerStyleSelected(
        val cheerStyleId: Int,
    ) : CheerStyleOnboardingEvent
}
