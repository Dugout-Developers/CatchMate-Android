package com.catchmate.presentation.viewmodel

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
        private val _postLoginRequest = MutableLiveData<PostLoginRequest>()
        val postLoginRequest: LiveData<PostLoginRequest>
            get() = _postLoginRequest

        private val _postLoginResponse = MutableLiveData<PostLoginResponse>()
        val postLoginResponse: LiveData<PostLoginResponse>
            get() = _postLoginResponse

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

        fun googleLogin() {
            viewModelScope.launch {
                try {
                    _postLoginRequest.value = socialLoginUseCase.loginWithGoogle()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun postAuthLogin(postLoginRequest: PostLoginRequest) {
            viewModelScope.launch {
                _postLoginResponse.value = postAuthLoginUseCase.postAuthLogin(postLoginRequest)
            }
        }
    }
