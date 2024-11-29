package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catchmate.domain.usecase.local.LocalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalDataViewMdoel
    @Inject
    constructor(
        private val localDataUseCase: LocalDataUseCase,
    ) : ViewModel() {
        private var _accessToken = MutableLiveData<String>()
        val accessToken: LiveData<String>
            get() = _accessToken

        private var _refreshToken = MutableLiveData<String>()
        val refreshToken: LiveData<String>
            get() = _refreshToken

        private var _userId = MutableLiveData<Long>()
        val userId: LiveData<Long>
            get() = _userId

        fun saveAccessToken(accessToken: String) {
            localDataUseCase.saveAccessToken(accessToken)
        }

        fun saveRefreshToken(refreshToken: String) {
            localDataUseCase.saveRefreshToken(refreshToken)
        }

        fun saveUserId(userId: Long) {
            localDataUseCase.saveUserId(userId)
        }

        fun getAccessToken() {
            _accessToken.value = localDataUseCase.getAccessToken()
        }

        fun getRefreshToken() {
            _refreshToken.value = localDataUseCase.getRefreshToken()
        }

        fun getUserId() {
            _userId.value = localDataUseCase.getUserId()
        }
    }
