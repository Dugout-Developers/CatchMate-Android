package com.catchmate.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.auth.PostLoginRequest
import com.catchmate.domain.model.auth.PostLoginResponse
import com.catchmate.domain.usecase.auth.PostAuthLoginUseCase
import com.catchmate.domain.usecase.auth.SocialLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val socialLoginUseCase: SocialLoginUseCase,
        private val postAuthLoginUseCase: PostAuthLoginUseCase,
    ) : ViewModel() {
        private val _postLoginRequest = MutableLiveData<PostLoginRequest?>()
        val postLoginRequest: LiveData<PostLoginRequest?>
            get() = _postLoginRequest

        private val _postLoginResponse = MutableLiveData<PostLoginResponse>()
        val postLoginResponse: LiveData<PostLoginResponse>
            get() = _postLoginResponse

        private val _noCredentialException = MutableLiveData<String>()
        val noCredentialException: LiveData<String>
            get() = _noCredentialException

        fun kakaoLogin() {
            viewModelScope.launch {
                _postLoginRequest.value = socialLoginUseCase.loginWithKakao()
            }
        }

        fun naverLogin() {
            viewModelScope.launch {
                _postLoginRequest.value = socialLoginUseCase.loginWithNaver()
            }
        }

        fun googleLogin(activity: Activity) {
            viewModelScope.launch {
                val result = socialLoginUseCase.loginWithGoogle(activity)
                if (result != null) {
                    _postLoginRequest.value = result!!
                } else {
                    _noCredentialException.value = "앱 로그인을 위해서 기기에 Google 계정을 추가해주세요."
                }
            }
        }

        fun postAuthLogin(postLoginRequest: PostLoginRequest) {
            viewModelScope.launch {
                _postLoginResponse.value = postAuthLoginUseCase.postAuthLogin(postLoginRequest)
            }
        }
    }
