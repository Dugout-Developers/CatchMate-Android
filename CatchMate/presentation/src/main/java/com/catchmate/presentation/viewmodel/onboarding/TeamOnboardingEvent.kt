package com.catchmate.presentation.viewmodel.onboarding

sealed interface TeamOnboardingEvent {
    object OnBackClicked : TeamOnboardingEvent

    object OnSubmitClicked : TeamOnboardingEvent

    data class OnTeamSelected(
        val clubId: Int,
    ) : TeamOnboardingEvent
}
