package com.catchmate.domain.model.enroll

data class ReceivedEnrollInfo(
    val enrollId: Long,
    val description: String,
    val newEnroll: Boolean,
    val requestDate: String,
    val applicant: EnrollUserInfo,
)
