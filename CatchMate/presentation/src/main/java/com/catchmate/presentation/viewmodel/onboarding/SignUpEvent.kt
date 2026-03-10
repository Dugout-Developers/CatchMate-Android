package com.catchmate.presentation.viewmodel.onboarding

sealed interface SignUpEvent {
    object OnBackClicked : SignUpEvent

    object OnNicknameClearClicked : SignUpEvent

    object OnSubmitClicked : SignUpEvent

    data class OnBirthDateChanged(
        val birthDate: String,
    ) : SignUpEvent

    data class OnNicknameChanged(
        val nickname: String,
    ) : SignUpEvent

    data class OnGenderSelected(
        val gender: String,
    ) : SignUpEvent
}
