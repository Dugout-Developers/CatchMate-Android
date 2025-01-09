package com.catchmate.data.dto.enroll

data class EnrollBoardInfoDTO(
    val boardInfo: Long,
    val title: String,
    val content: String,
    val cheerClubId: Int,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferredGender: String,
    val preferredAgeRange: List<String>,
    val gameInfo: GameInfoDTO,
    val liftUpDate: String,
    val userInfo: UserInfoDTO,
)
