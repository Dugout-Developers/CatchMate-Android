package com.catchmate.data.dto.board

data class PostBoardRequestDTO(
    val title: String?,
    val content: String?,
    val maxPerson: Int?,
    val cheerClubId: Int?,
    val preferredGender: String?,
    val preferredAgeRange: List<String>?,
    val completed: Boolean, // 임시 저장 시 false, 게시글 등록 시 true
    val gameCreateRequest: GameRequestDto?,
) // 임시 저장 시 미입력 항목은 전부 null로 보냄
