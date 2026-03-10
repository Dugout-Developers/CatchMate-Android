package com.catchmate.presentation.viewmodel.login

import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest

// 로그인 화면에서의 상태들을 정의
data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val signupRequiredInfo: PostUserAdditionalInfoRequest? = null,
    val isLoginSuccess: Boolean = false,
)
