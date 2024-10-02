package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val userUseCase: UserUseCase,
    ) : ViewModel() {
        private val _userProfile = MutableLiveData<GetUserProfileResponse?>()
        val userProfile: LiveData<GetUserProfileResponse?>
            get() = _userProfile

        fun getUserProfile() {
            viewModelScope.launch {
                _userProfile.value = userUseCase.getUserProfile()
            }
        }
    }
