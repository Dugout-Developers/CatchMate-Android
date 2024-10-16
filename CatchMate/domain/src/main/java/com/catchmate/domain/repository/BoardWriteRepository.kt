package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardEditRequest
import com.catchmate.domain.model.BoardWriteRequest
import com.catchmate.domain.model.BoardWriteResponse

interface BoardWriteRepository {
    suspend fun postBoardWrite(boardWriteRequest: BoardWriteRequest): BoardWriteResponse?

    suspend fun putBoard(boardEditRequest: BoardEditRequest): BoardWriteResponse?
}
