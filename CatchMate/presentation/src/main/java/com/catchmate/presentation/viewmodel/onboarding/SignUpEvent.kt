package com.catchmate.presentation.viewmodel.onboarding

sealed interface SignUpEvent {
    data object OnBackClicked : SignUpEvent
    data object OnNicknameClearClicked : SignUpEvent
    data class OnSubmitClicked(
        val nickname: String,
        val birthDate: String,
        val gender: String,
    ) : SignUpEvent
    data class OnBirthDateChanged(val birthDate: String) : SignUpEvent
    data class OnNicknameChanged(val nickname: String) : SignUpEvent
    data class OnGenderSelected(val gender: String) : SignUpEvent
}
