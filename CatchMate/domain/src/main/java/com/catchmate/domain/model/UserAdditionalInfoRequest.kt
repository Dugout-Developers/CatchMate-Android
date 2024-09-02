package com.catchmate.domain.model

import java.io.Serializable

data class UserAdditionalInfoRequest(
    val email: String,
    val provider: String,
    val providerId: String,
    val gender: String,
    val picture: String,
    val fcmToken: String,
    val nickName: String,
    val birthDate: String,
    val favGudan: String,
    val watchStyle: String,
) : Serializable
