package com.catchmate.data.mapper

import com.catchmate.data.dto.BoardDeleteRequestDTO
import com.catchmate.domain.model.BoardDeleteRequest

object BoardReadMapper {
    fun toBoardDeleteRequestDTO(boardDeleteRequest: BoardDeleteRequest): BoardDeleteRequestDTO =
        BoardDeleteRequestDTO(
            boardId = boardDeleteRequest.boardId,
        )
}
