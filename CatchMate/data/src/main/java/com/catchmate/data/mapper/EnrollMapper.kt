package com.catchmate.data.mapper

import com.catchmate.data.dto.EnrollCancelResponseDTO
import com.catchmate.data.dto.EnrollRequestDTO
import com.catchmate.data.dto.EnrollResponseDTO
import com.catchmate.domain.model.EnrollCancelResponse
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollResponse

object EnrollMapper {
    fun toEnrollRequestDTO(enrollRequest: EnrollRequest): EnrollRequestDTO =
        EnrollRequestDTO(
            description = enrollRequest.description,
        )

    fun toEnrollResponse(enrollResponseDTO: EnrollResponseDTO): EnrollResponse =
        EnrollResponse(
            enrollId = enrollResponseDTO.enrollId,
            requestAt = enrollResponseDTO.requestAt,
        )

    fun toEnrollCancelResponse(enrollCancelResponseDTO: EnrollCancelResponseDTO): EnrollCancelResponse =
        EnrollCancelResponse(
            enrollId = enrollCancelResponseDTO.enrollId,
            deletedAt = enrollCancelResponseDTO.deletedAt,
        )
}
