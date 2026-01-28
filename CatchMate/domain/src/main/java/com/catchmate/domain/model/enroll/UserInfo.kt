package com.catchmate.domain.model.enroll

import android.os.Parcelable
import com.catchmate.domain.model.user.Club
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val userId: Long,
    val email: String,
    val profileImageUrl: String,
    val gender: String,
    val allAlarm: String,
    val chatAlarm: String,
    val enrollAlarm: String,
    val eventAlarm: String,
    val nickName: String,
    val favoriteClub: Club,
    val birthDate: String,
    val watchStyle: String? = null,
) : Parcelable
