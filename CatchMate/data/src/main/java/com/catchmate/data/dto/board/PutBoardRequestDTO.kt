package com.catchmate.data.dto.board

data class PutBoardRequestDTO(
    val title: String,
    val content: String,
    val maxPerson: Int,
    val cheerClubId: Int,
    val preferredGender: String?,
    val preferredAgeRange: List<String>?,
    val completed: Boolean,
    val gameRequest: GameRequestDto,
)
