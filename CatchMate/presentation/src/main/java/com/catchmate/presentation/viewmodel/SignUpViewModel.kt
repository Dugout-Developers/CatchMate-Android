package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.CheckNicknameResponse
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.domain.model.UserResponse
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
        private val _checkNicknameResponse = MutableLiveData<CheckNicknameResponse>()
        val checkNicknameResponse: LiveData<CheckNicknameResponse>
            get() = _checkNicknameResponse

        private var _userResponse = MutableLiveData<UserResponse>()
        val userResponse
            get() = _userResponse

        fun getNicknameAvailability(nickName: String) {
            viewModelScope.launch {
                _checkNicknameResponse.value = signUpUseCase.getNicknameAvailability(nickName)
            }
        }

        fun postUserAdditionalInfo(userAdditionalInfoRequest: UserAdditionalInfoRequest) {
            viewModelScope.launch {
                _userResponse.value = signUpUseCase.postUserAdditionalInfo(userAdditionalInfoRequest)
            }
        }
    }
