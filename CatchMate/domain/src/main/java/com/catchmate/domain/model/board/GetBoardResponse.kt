package com.catchmate.domain.model.board

import android.os.Parcelable
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GetBoardResponse(
    val boardId: Long,
    val title: String,
    val content: String,
    val cheerClubId: Int,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferredGender: String,
    val preferredAgeRange: String,
    val gameInfo: GameInfo,
    val liftUpDate: String,
    val userInfo: UserInfo,
) : Parcelable
