package com.catchmate.domain.model.enumclass

enum class EnrollState {
    APPLY, // 신청 내역이 없는 경우
    CANCEL, // 신청 상태가 PENDING 인 경우
    VIEW_CHAT, // 사용자가 작성자일 경우, 신청 상태가 ACCEPTED 인 경우
    REJECTED, // 신청 상태가 REJECTED 인 경우
}
