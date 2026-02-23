package com.catchmate.presentation.viewmodel.onboarding

sealed interface TeamOnboardingEvent {
    data object OnBackClicked : TeamOnboardingEvent
    data object OnSubmitClicked : TeamOnboardingEvent
    data class OnTeamSelected(val clubId: Int) : TeamOnboardingEvent
}
