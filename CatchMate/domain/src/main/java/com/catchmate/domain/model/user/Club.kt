package com.catchmate.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Club(
    val clubId: Int,
    val name: String,
    val homeStadium: String,
    val region: String,
) : Parcelable
