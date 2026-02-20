package com.catchmate.presentation.viewmodel.onboarding

data class SignUpUiState(
    val isSubmitButtonEnable: Boolean = false,
    val nickname: String = "",
    val birthDate: String = "",
    val gender: String = "여성",
    val isNicknameValid: Boolean = false,
)
