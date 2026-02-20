package com.catchmate.presentation.viewmodel.onboarding

sealed interface SignUpSideEffect {
    data object NavigateToBack : SignUpSideEffect
    data class NavigateToNext(
        val nickname: String,
        val birthDate: String,
        val gender: String,
    ) : SignUpSideEffect
    data class ShowSnackBar(val message: String) : SignUpSideEffect
}
