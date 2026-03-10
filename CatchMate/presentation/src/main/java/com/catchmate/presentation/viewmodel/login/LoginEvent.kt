package com.catchmate.presentation.viewmodel.login

import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest

// 단발성 이벤트(네비게이션, 스낵바 등)를 위한 이벤트 정의
sealed interface LoginEvent {
    data class NavigateToSignUp(
        val userInfo: PostUserAdditionalInfoRequest,
    ) : LoginEvent

    object NavigateToHome : LoginEvent

    data class ShowSnackBar(
        val message: String,
    ) : LoginEvent
}
