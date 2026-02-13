package com.catchmate.presentation.viewmodel.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.GoogleLoginException
import com.catchmate.domain.exception.Result
import com.catchmate.domain.model.auth.PostLoginRequest
import com.catchmate.domain.model.auth.UserData
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.usecase.auth.PostAuthLoginUseCase
import com.catchmate.domain.usecase.auth.SocialLoginUseCase
import com.catchmate.domain.usecase.local.LocalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val socialLoginUseCase: SocialLoginUseCase,
        private val postAuthLoginUseCase: PostAuthLoginUseCase,
        private val localDataUseCase: LocalDataUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LoginUiState())
        val uiState = _uiState.asStateFlow()

        private val _event = MutableSharedFlow<LoginEvent>()
        val event = _event.asSharedFlow()

        private fun handleSocialLoginResult(userData: UserData?) {
            if (userData == null) return

            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                val request =
                    PostLoginRequest(
                        userData.provider,
                        userData.providerId,
                        userData.fcmToken,
                    )
                val response = postAuthLoginUseCase(request)

                if (response != null) {
                    if (response.signupRequired) {
                        val userInfo =
                            PostUserAdditionalInfoRequest(
                                userData.email,
                                userData.providerId,
                                userData.provider,
                                userData.profileImageUrl,
                                userData.fcmToken,
                                "",
                                "",
                                "",
                                -1,
                                "",
                            )
                        _event.emit(LoginEvent.NavigateToSignUp(userInfo))
                    } else {
                        _event.emit(LoginEvent.NavigateToHome)
                    }
                }
                _uiState.update { it.copy(isLoading = false) }
            }
        }

        fun kakaoLogin() {
            viewModelScope.launch {
                handleSocialLoginResult(socialLoginUseCase.loginWithKakao())
            }
        }

        fun naverLogin(activity: Activity) {
            viewModelScope.launch {
                handleSocialLoginResult(socialLoginUseCase.loginWithNaver(activity))
            }
        }

        fun googleLogin(activity: Activity) {
            viewModelScope.launch {
                val result = socialLoginUseCase.loginWithGoogle(activity)
                when (result) {
                    is Result.Success -> {
                        handleSocialLoginResult(result.data)
                    }

                    is Result.Error -> {
                        val errorMessage = when (result.exception) {
                            is GoogleLoginException.Cancelled -> {
                                Log.e("GoogleLoginError", "로그인이 취소되었습니다.")
                                null
                            }

                            is GoogleLoginException.NoCredentials -> {
                                "앱 로그인을 위해서 기기에 Google 계정을 등록해주세요."
                            }

                            is GoogleLoginException.TokenParsing -> {
                                "로그인 정보 처리 중 오류가 발생했습니다."
                            }

                            is GoogleLoginException.Unknown -> {
                                "알 수 없는 오류가 발생했습니다."
                            }

                            else -> {
                                "로그인 중 오류가 발생했습니다."
                            }
                        }

                        errorMessage?.let { msg ->
                            _event.emit(LoginEvent.ShowSnackBar(msg))
                        }
                    }
                }
            }
        }
    }