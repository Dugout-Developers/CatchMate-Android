package com.catchmate.domain.model.board

import android.os.Parcelable
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.user.Club
import kotlinx.parcelize.Parcelize

@Parcelize
data class Board(
    val boardId: Long,
    val title: String,
    val content: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val bookMarked: Boolean,
    val cheerClub: Club,
    val gameResponse: GameInfo,
    val userResponse: UserInfo,
) : Parcelable
