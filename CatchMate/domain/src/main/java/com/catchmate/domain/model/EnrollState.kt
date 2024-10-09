package com.catchmate.domain.model

enum class EnrollState {
    APPLICABLE,     // 신청 가능
    ACCEPTED,       // 신청 수락됨(참여중)
    REJECTED,       // 신청 거절됨
    PENDING,        // 신청 후 대기중
}
