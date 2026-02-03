package com.catchmate.domain.model.user

import java.io.Serializable

data class PatchUserProfileResponse(
    val userId: Long,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val allAlarm: String,
    val chatAlarm: String,
    val enrollAlarm: String,
    val eventAlarm: String,
    val nickName: String,
    val club: Club,
    val birthDate: String,
    val watchStyle: String?,
) : Serializable
