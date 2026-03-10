package com.catchmate.domain.model.board

import android.os.Parcelable
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.user.Club
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetBoardResponse(
    val boardId: Long,
    val title: String,
    val content: String,
    val currentPerson: Int,
    val maxPerson: Int,
    val preferredGender: String?,
    val preferredAgeRange: String,
    val liftUpDate: String,
    val bookMarked: Boolean,
    val buttonStatus: String,
    val myEnrollId: Long?,
    val chatRoomId: Long,
    val cheerClub: Club,
    val game: GameInfo,
    val user: UserInfo,
) : Parcelable
