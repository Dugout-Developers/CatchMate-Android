package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
    ) : ViewModel() {
        private val _loginResponse = MutableLiveData<String>()
        val loginResponse: LiveData<String>
            get() = _loginResponse

        fun kakaoLogin() {
            viewModelScope.launch {
                loginUseCase.loginWithKakao()
            }
        }

        fun naverLogin() {
            viewModelScope.launch {
                loginUseCase.loginWithNaver()
            }
        }
    }
