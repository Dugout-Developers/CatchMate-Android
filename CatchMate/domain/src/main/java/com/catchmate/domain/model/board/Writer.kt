package com.catchmate.domain.model.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Writer(
    val userId: Long,
    val nickName: String,
    val picture: String,
    val favGudan: String,
    val watchStyle: String,
    val gender: String,
    val birthDate: String,
) : Parcelable
