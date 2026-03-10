package com.catchmate.presentation.viewmodel.favorite

sealed interface FavoriteSideEffect {
    data class NavigateToReadPost(val boardId: Long) : FavoriteSideEffect

    object NavigateToLogin : FavoriteSideEffect

    object ShowSnackBar : FavoriteSideEffect
}
