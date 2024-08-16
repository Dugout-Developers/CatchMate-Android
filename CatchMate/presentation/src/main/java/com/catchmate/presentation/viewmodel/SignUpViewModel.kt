package com.catchmate.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserResponse
import com.catchmate.domain.usecase.AuthUseCase
import com.catchmate.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
    ) : ViewModel() {
        private var _checkNicknameresponse = MutableLiveData<CheckNicknameResponse>()
        val checkNicknameResponse
            get() = _checkNicknameresponse

        private var _userResponse = MutableLiveData<UserResponse>()
        val userResponse
            get() = _userResponse

        fun getNicknameAvailability(
            accessToken: String,
            nickName: String,
        ) {
            viewModelScope.launch {
                _checkNicknameresponse.value = signUpUseCase.getNicknameAvailability(accessToken, nickName)
            }
        }

        fun patchUserAdditionalInfo(
            accessToken: String,
            userAdditionalInfoRequest: UserAdditionalInfoRequest,
        ) {
            viewModelScope.launch {
                _userResponse.value = signUpUseCase.patchUserAdditionalInfo(accessToken, userAdditionalInfoRequest)
            }
        }
    }
