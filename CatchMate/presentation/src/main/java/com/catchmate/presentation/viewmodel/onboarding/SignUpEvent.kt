package com.catchmate.presentation.viewmodel.onboarding

sealed interface SignUpEvent {
    data object OnBackClicked : SignUpEvent
    data object OnNicknameClearClicked : SignUpEvent
    data object OnSubmitClicked : SignUpEvent
    data class OnBirthDateChanged(val birthDate: String) : SignUpEvent
    data class OnNicknameChanged(val nickname: String) : SignUpEvent
    data class OnGenderSelected(val gender: String) : SignUpEvent
}
