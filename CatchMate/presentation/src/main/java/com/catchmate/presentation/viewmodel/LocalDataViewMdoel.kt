package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catchmate.domain.usecase.LocalDataUseCase
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

        fun saveAccessToken(accessToken: String) {
            localDataUseCase.saveAccessToken(accessToken)
        }

        fun saveRefreshToken(refreshToken: String) {
            localDataUseCase.saveRefreshToken(refreshToken)
        }

        fun getAccessToken() {
            _accessToken.value = localDataUseCase.getAccessToken()
        }

        fun getRefreshToken() {
            _refreshToken.value = localDataUseCase.getRefreshToken()
        }
    }
