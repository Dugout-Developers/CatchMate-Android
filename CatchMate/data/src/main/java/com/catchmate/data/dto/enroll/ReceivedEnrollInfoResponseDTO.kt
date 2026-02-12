package com.catchmate.data.dto.enroll

data class ReceivedEnrollInfoResponseDTO(
    val enrollId: Long,
    val description: String,
    val requestDate: String,
    val newEnroll: Boolean,
    val applicantResponse: EnrollUserInfoDto,
)
