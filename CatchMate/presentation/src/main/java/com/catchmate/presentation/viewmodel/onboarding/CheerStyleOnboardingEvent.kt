package com.catchmate.presentation.viewmodel.onboarding

interface CheerStyleOnboardingEvent {
    data object OnBackClicked : CheerStyleOnboardingEvent
    data object OnSubmitClicked : CheerStyleOnboardingEvent
    data class OnCheerStyleSelected(val cheerStyleId: Int) : CheerStyleOnboardingEvent
}
