package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.GetBoardPagingResponse
import com.catchmate.domain.model.GetUserProfileResponse
import com.catchmate.domain.usecase.board.GetBoardPagingUseCase
import com.catchmate.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getUserProfileUseCase: GetUserProfileUseCase,
        private val getBoardPagingUseCase: GetBoardPagingUseCase,
    ) : ViewModel() {
        private val _userProfile = MutableLiveData<GetUserProfileResponse>()
        val userProfile: LiveData<GetUserProfileResponse>
            get() = _userProfile

        private val _getBoardPagingResponse = MutableLiveData<List<GetBoardPagingResponse>>()
        val getBoardPagingResponse: LiveData<List<GetBoardPagingResponse>>
            get() = _getBoardPagingResponse

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        fun getUserProfile() {
            viewModelScope.launch {
                val result = getUserProfileUseCase.getUserProfile()
                result
                    .onSuccess { user ->
                        _userProfile.value = user
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun getBoardPaging(
            pageNum: Long,
            gudans: String = "",
            people: Int = 0,
            gameDate: String = "9999-99-99",
        ) {
            viewModelScope.launch {
                val result = getBoardPagingUseCase.getBoardPaging(pageNum, gudans, people, gameDate)
                result
                    .onSuccess { boardList ->
                        _getBoardPagingResponse.value = boardList
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
