package com.catchmate.data.dto.enroll

data class ReceivedEnrollInfoDTO(
    val enrollId: Long,
    val description: String,
    val newEnroll: Boolean,
    val requestDate: String,
    val applicant: EnrollUserInfoDto,
)
