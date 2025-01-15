package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catchmate.domain.usecase.user.PatchUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
    @Inject
    constructor(
        private val patchUserProfileUseCase: PatchUserProfileUseCase,
    ) : ViewModel() {
        private val _profileImage = MutableLiveData<String>()
        val profileImage: LiveData<String>
            get() = _profileImage

        private val _nickName = MutableLiveData<String>()
        val nickName: LiveData<String>
            get() = _nickName

        private val _cheerClub = MutableLiveData<Int>()
        val cheerClub: LiveData<Int>
            get() = _cheerClub

        private val _watchStyle = MutableLiveData<String>()
        val watchStyle: LiveData<String>
            get() = _watchStyle

        fun setProfileImage(url: String) {
            _profileImage.value = url
        }

        fun setNickName(str: String) {
            _nickName.value = str
        }

        fun setCheerClub(id: Int) {
            _cheerClub.value = id
        }

        fun setWatchStyle(str: String) {
            _watchStyle.value = str
        }
    }
