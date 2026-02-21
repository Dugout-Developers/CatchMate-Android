package com.catchmate.domain.model.chatting

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetChattingCrewListResponse(
    val memberId: Long,
    val userId: Long,
    val nickName: String,
    val profileImageUrl: String,
    val joinedAt: String,
) : Parcelable
