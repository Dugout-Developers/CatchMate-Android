package com.catchmate.data.dto.board

data class PostBoardRequestDTO(
    val boardId: Long? = null,  // 임시저장 완성 시 임시저장 게시글 boardId 삽입, 새 게시글 생성 시 null
    val title: String?,
    val content: String?,
    val maxPerson: Int?,
    val cheerClubId: Int?,
    val preferredGender: String?,
    val preferredAgeRange: List<String>?,
    val completed: Boolean, // 임시 저장 시 false, 게시글 작성 시 true
    val gameRequest: GameRequestDto?,
)   // 임시 저장 시 미입력 항목은 전부 null로 보냄
