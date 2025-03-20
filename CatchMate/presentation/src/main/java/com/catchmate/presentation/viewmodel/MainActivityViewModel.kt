package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
    @Inject
    constructor() : ViewModel() {
        private val _isGuestLogin = MutableLiveData<Boolean>()
        val isGuestLogin: LiveData<Boolean> get() = _isGuestLogin

        fun setGuestLogin(isGuest: Boolean) {
            _isGuestLogin.value = isGuest
        }
    }
