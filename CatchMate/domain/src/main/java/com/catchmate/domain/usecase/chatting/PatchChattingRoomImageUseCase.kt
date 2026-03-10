package com.catchmate.domain.usecase.chatting

import com.catchmate.domain.repository.ChattingRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PatchChattingRoomImageUseCase
    @Inject
    constructor(
        private val chattingRepository: ChattingRepository,
    ) {
        suspend operator fun invoke(
            roomId: Long,
            chatRoomImage: MultipartBody.Part,
        ): Result<Unit> = chattingRepository.patchChattingRoomImage(roomId, chatRoomImage)
    }
