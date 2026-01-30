package com.catchmate.domain.model.enroll

import android.os.Parcelable
import com.catchmate.domain.model.user.Club
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val userId: Long,
    val nickName: String,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val birthDate: String,
    val watchStyle: String? = null,
    val club: Club,
) : Parcelable
