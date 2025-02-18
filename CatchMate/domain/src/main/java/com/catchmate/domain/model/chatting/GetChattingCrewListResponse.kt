package com.catchmate.domain.model.chatting

import com.catchmate.domain.model.user.GetUserProfileResponse

data class GetChattingCrewListResponse(
    val userInfoList: List<GetUserProfileResponse>,
)
