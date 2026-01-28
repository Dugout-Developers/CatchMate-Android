package com.catchmate.data.dto.user

data class GetUserProfileResponseDTO(
    val userId: Long,
    val nickName: String,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val birthDate: String,
    val watchStyle: String?,
    val club: ClubDTO,
)
