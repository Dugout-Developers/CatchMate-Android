package com.catchmate.data.repository

import com.catchmate.data.datasource.local.LocalStorageDataSource
import com.catchmate.domain.repository.LocalDataRepository
import javax.inject.Inject

class LocalDataRepositoryImpl
    @Inject
    constructor(
        private val localStorageDataSource: LocalStorageDataSource,
    ) : LocalDataRepository {
        override fun saveAccessToken(accessToken: String) {
            localStorageDataSource.saveAccessToken(accessToken)
        }

        override fun saveRefreshToken(refreshToken: String) {
            localStorageDataSource.saveRefreshToken(refreshToken)
        }

        override fun getAccessToken(): String = localStorageDataSource.getAccessToken()

        override fun getRefreshToken(): String = localStorageDataSource.getRefreshToken()

        override fun removeTokens() {
            localStorageDataSource.removeTokens()
        }
    }
