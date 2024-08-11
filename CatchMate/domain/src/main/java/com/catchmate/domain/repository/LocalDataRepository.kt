package com.catchmate.domain.repository

interface LocalDataRepository {
    fun saveAccessToken(accessToken: String)

    fun saveRefreshToken(refreshToken: String)

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun removeTokens()
}
