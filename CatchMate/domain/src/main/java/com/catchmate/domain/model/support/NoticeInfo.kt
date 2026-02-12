package com.catchmate.domain.model.support

data class NoticeInfo(
    val noticeId: Long,
    val title: String,
    val content: String,
    val writerNickname: String,
    val createdAt: String,
)
