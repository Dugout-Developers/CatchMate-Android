package com.catchmate.data.mapper

import com.catchmate.data.dto.PatchEnrollRejectResponseDTO
import com.catchmate.data.dto.PostEnrollRequestDTO
import com.catchmate.data.dto.PostEnrollResponseDTO
import com.catchmate.domain.model.PatchEnrollRejectResponse
import com.catchmate.domain.model.PostEnrollRequest
import com.catchmate.domain.model.PostEnrollResponse

object EnrollMapper {
    fun toPostEnrollRequestDTO(request: PostEnrollRequest): PostEnrollRequestDTO =
        PostEnrollRequestDTO(
            description = request.description,
        )

    fun toPostEnrollResponse(responseDTO: PostEnrollResponseDTO): PostEnrollResponse =
        PostEnrollResponse(
            enrollId = responseDTO.enrollId,
            requestAt = responseDTO.requestAt,
        )

    fun toPatchEnrollRejectResponse(responseDTO: PatchEnrollRejectResponseDTO): PatchEnrollRejectResponse =
        PatchEnrollRejectResponse(
            enrollId = responseDTO.enrollId,
            acceptStatus = responseDTO.acceptStatus,
        )
}
