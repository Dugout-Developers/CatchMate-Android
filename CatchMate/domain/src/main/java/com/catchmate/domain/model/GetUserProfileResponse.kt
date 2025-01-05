package com.catchmate.domain.model

data class GetUserProfileResponse(
    val userId: Long,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val allAlarm: String,
    val chatAlarm: String,
    val enrollAlarm: String,
    val eventAlarm: String,
    val nickName: String,
    val favoriteClub: FavoriteClub,
    val birthDate: String,
    val watchStyle: String?,
)
