package com.catchmate.data.dto.enroll

data class AllReceivedEnrollInfoResponseDTO(
    val boardResponse: EnrollBoardInfoDTO,
    val enrollResponses: List<ReceivedEnrollInfoDTO>,
)
