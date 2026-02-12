package com.catchmate.domain.model.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class BoardMode : Parcelable {
    @Parcelize
    object New : BoardMode() // 새 게시글 등록

    @Parcelize
    data class Temp(
        val boardId: Long,
    ) : BoardMode() // 임시저장 불러오기

    @Parcelize
    data class Edit(
        val boardInfo: GetBoardResponse,
    ) : BoardMode() // 기존 게시글 수정
}
