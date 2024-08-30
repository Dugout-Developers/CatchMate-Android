package com.catchmate.domain.repository

import com.catchmate.domain.model.BoardWriteRequest
import com.catchmate.domain.model.BoardWriteResponse

interface BoardWriteRepository {
    suspend fun postBoardWrite(accessToken: String, boardWriteRequest: BoardWriteRequest): BoardWriteResponse?
}
