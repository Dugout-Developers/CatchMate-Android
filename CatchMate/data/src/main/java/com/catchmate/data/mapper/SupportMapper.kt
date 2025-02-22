package com.catchmate.data.mapper

import com.catchmate.data.dto.support.PostInquiryRequestDTO
import com.catchmate.data.dto.support.PostInquiryResponseDTO
import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.domain.model.support.PostInquiryResponse

object SupportMapper {
    fun toPostInquiryRequestDTO(request: PostInquiryRequest): PostInquiryRequestDTO =
        PostInquiryRequestDTO(
            inquiryType = request.inquiryType,
            content = request.content,
        )

    fun toPostInquiryResponse(dto: PostInquiryResponseDTO): PostInquiryResponse =
        PostInquiryResponse(
            state = dto.state,
        )
}
