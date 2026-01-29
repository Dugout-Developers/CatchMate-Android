package com.catchmate.data.dto.board

data class PostBoardRequestDTO(
    val boardId: Long? = null,  // 임시저장 완성 시 임시저장 게시글 boardId 삽입, 새 게시글 생성 시 null
    val title: String,
    val content: String,
    val maxPerson: Int,
    val cheerClubId: Int,
    val preferredGender: String, // 미 선택 시 빈 문자열
    val preferredAgeRange: List<String>, // 미 선택 시 빈 배열
    val completed: Boolean, // 임시 저장 시 false, 게시글 작성 시 true
    val gameRequest: GameRequestDto,
)
