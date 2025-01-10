package com.catchmate.domain.model.enroll

data class EnrollBoardInfo(
    val boardInfo: Long,
    val title: String,
    val content: String,
    val cheerClubId: Int,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferredGender: String,
    val preferredAgeRange: List<String>,
    val gameInfo: GameInfo,
    val liftUpDate: String,
    val userInfo: UserInfo,
)
