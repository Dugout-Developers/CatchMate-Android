package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetCheckNicknameResponse
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.domain.model.user.PostUserAdditionalInfoResponse
import com.catchmate.domain.usecase.auth.GetAuthCheckNicknameUseCase
import com.catchmate.domain.usecase.user.PostUserAdditionalInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val getAuthCheckNicknameUseCase: GetAuthCheckNicknameUseCase,
        private val postUserAdditionalInfoUseCase: PostUserAdditionalInfoUseCase,
    ) : ViewModel() {
        private val _getCheckNicknameResponse = MutableLiveData<GetCheckNicknameResponse>()
        val getCheckNicknameResponse: LiveData<GetCheckNicknameResponse>
            get() = _getCheckNicknameResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        private var _userAdditionalInfoResponse = MutableLiveData<PostUserAdditionalInfoResponse>()
        val userAdditionalInfoResponse
            get() = _userAdditionalInfoResponse

        fun getAuthCheckNickname(nickName: String) {
            viewModelScope.launch {
                val result = getAuthCheckNicknameUseCase.getAuthCheckNickname(nickName)
                result
                    .onSuccess { availability ->
                        _getCheckNicknameResponse.value = availability
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun postUserAdditionalInfo(userAdditionalInfoRequest: PostUserAdditionalInfoRequest) {
            viewModelScope.launch {
                val result = postUserAdditionalInfoUseCase.postUserAdditionalInfo(userAdditionalInfoRequest)
                result
                    .onSuccess { response ->
                        _userAdditionalInfoResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }
    }
