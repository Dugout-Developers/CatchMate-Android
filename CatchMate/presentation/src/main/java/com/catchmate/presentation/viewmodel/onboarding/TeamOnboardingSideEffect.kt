package com.catchmate.presentation.viewmodel.onboarding

sealed interface TeamOnboardingSideEffect {
    object NavigateToBack : TeamOnboardingSideEffect

    data class NavigateToNext(
        val clubId: Int,
    ) : TeamOnboardingSideEffect
}
