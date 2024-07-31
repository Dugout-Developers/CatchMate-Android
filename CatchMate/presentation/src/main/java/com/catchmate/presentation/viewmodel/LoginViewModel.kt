package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.LoginRequest
import com.catchmate.domain.model.LoginResponse
import com.catchmate.domain.usecase.AuthUseCase
import com.catchmate.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
        private val authUseCase: AuthUseCase,
    ) : ViewModel() {
        private val _loginRequest = MutableLiveData<LoginRequest>()
        val loginRequest: LiveData<LoginRequest>
            get() = _loginRequest

        private val _loginResponse = MutableLiveData<LoginResponse>()
        val loginResponse: LiveData<LoginResponse>
            get() = _loginResponse

        fun kakaoLogin() {
            viewModelScope.launch {
                _loginRequest.value = loginUseCase.loginWithKakao()
            }
        }

        fun naverLogin() {
            viewModelScope.launch {
                _loginRequest.value = loginUseCase.loginWithNaver()
            }
        }

        fun googleLogin() {
            viewModelScope.launch {
                try {
                    _loginRequest.value = loginUseCase.loginWithGoogle()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun postLogin(loginRequest: LoginRequest) {
            viewModelScope.launch {
                _loginResponse.value = authUseCase.postLogin(loginRequest)
            }
        }
    }
