package com.catchmate.data.dto.support

data class NoticeInfoDTO(
    val noticeId: Long,
    val title: String,
    val content: String,
    val writerNickname: String,
    val createdAt: String,
)
