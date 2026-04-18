package com.catchmate.presentation.viewmodel.board

sealed interface WriteBoardEvent {
    object OnBackClicked : WriteBoardEvent

    object OnSubmitClicked : WriteBoardEvent

    object InitData : WriteBoardEvent

    data class OnSaveTempClicked(
        val boardId: Long? = null,
    ) : WriteBoardEvent

    data class OnLocationClicked(
        val location: String? = null,
    ) : WriteBoardEvent

    data class OnDateTimeClicked(
        val dateTime: String? = null,
    ) : WriteBoardEvent
}
