package com.catchmate.domain.model.enroll

data class AllReceivedEnrollInfoResponse(
    val boardResponse: EnrollBoardInfo,
    val enrollResponses: List<ReceivedEnrollInfo>,
)
