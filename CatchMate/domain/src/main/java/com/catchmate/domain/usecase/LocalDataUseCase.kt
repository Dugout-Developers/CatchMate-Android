package com.catchmate.domain.usecase

import com.catchmate.domain.repository.LocalDataRepository
import javax.inject.Inject

class LocalDataUseCase
    @Inject
    constructor(
        private val localDataRepository: LocalDataRepository,
    ) {
        fun saveAccessToken(accessToken: String) {
            localDataRepository.saveAccessToken(accessToken)
        }

        fun saveRefreshToken(refreshToken: String) {
            localDataRepository.saveRefreshToken(refreshToken)
        }

        fun getAccessToken() : String = localDataRepository.getAccessToken()

        fun getRefreshToken() : String = localDataRepository.getRefreshToken()

        fun removeTokens() {
            localDataRepository.removeTokens()
        }
    }
