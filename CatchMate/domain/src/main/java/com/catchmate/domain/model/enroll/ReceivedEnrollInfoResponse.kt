package com.catchmate.domain.model.enroll

data class ReceivedEnrollInfoResponse(
    val enrollId: Long,
    val description: String,
    val requestDate: String,
    val newEnroll: Boolean,
    val applicantResponse: EnrollUserInfo,
)
