package com.catchmate.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetUserProfileResponse(
    val userId: Long,
    val nickName: String,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val birthDate: String,
    val watchStyle: String?,
    val club: Club,
) : Parcelable
