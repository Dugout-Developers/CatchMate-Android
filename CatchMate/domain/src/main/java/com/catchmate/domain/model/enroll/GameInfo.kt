package com.catchmate.domain.model.enroll

import android.os.Parcelable
import com.catchmate.domain.model.user.Club
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameInfo(
    val gameId: Int,
    val gameStartDate: String?,
    val location: String?,
    val homeClub: Club?,
    val awayClub: Club?,
) : Parcelable
