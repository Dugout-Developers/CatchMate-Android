package com.catchmate.data.dto.user

data class PatchUserProfileResponseDTO(
    val userId: Long,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val allAlarm: String,
    val chatAlarm: String,
    val enrollAlarm: String,
    val eventAlarm: String,
    val nickName: String,
    val club: ClubDTO,
    val birthDate: String,
    val watchStyle: String?,
)
