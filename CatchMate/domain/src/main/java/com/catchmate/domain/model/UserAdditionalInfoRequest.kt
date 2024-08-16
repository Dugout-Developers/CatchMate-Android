package com.catchmate.domain.model

import java.io.Serializable

data class UserAdditionalInfoRequest(
    val gender: String,
    val nickName: String,
    val birthDate: String,
    val favGudan: String,
    val watchStyle: String,
) : Serializable
