package com.catchmate.domain.model.board

data class PostBoardRequest(
    val boardId: Long? = null,
    val title: String,
    val content: String,
    val maxPerson: Int,
    val cheerClubId: Int,
    val preferredGender: String,
    val preferredAgeRange: List<String>,
    val completed: Boolean,
    val gameRequest: GameRequest,
)
