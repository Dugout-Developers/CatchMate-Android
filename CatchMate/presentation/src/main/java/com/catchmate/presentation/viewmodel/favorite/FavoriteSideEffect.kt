package com.catchmate.presentation.viewmodel.favorite

sealed interface FavoriteSideEffect {
    data class NavigateToReadPost(val boardId: Long) : FavoriteSideEffect
    data object NavigateToLogin : FavoriteSideEffect
    data object ShowSnackBar : FavoriteSideEffect
}
