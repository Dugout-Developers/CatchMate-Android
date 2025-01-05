package com.catchmate.data.dto

import com.catchmate.domain.model.FavoriteClub

data class GetUserProfileByIdResponseDTO(
    val userId: Long,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val allAlarm: String,
    val chatAlarm: String,
    val enrollAlarm: String,
    val eventAlarm: String,
    val nickName: String,
    val favoriteClub: FavoriteClubDTO,
    val birthDate: String,
    val watchStyle: String,
)
