package com.catchmate.presentation.viewmodel.favorite

sealed interface FavoriteEvent {
    object InitData : FavoriteEvent

    data class OnFavoriteBoardClicked(
        val boardId: Long,
    ) : FavoriteEvent

    data class OnFavoriteBoardUnliked(
        val boardId: Long,
    ) : FavoriteEvent

    object OnLoadMoreBoards : FavoriteEvent
}
