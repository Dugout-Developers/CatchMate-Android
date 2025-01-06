package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.exception.ReissueFailureException
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PostBoardResponse
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.board.PutBoardResponse
import com.catchmate.domain.usecase.board.PostBoardUseCase
import com.catchmate.domain.usecase.board.PutBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel
    @Inject
    constructor(
        private val postBoardUseCase: PostBoardUseCase,
        private val putBoardUseCase: PutBoardUseCase,
    ) : ViewModel() {
        private var _homeTeamName = MutableLiveData<String>()
        val homeTeamName: LiveData<String>
            get() = _homeTeamName

        private var _awayTeamName = MutableLiveData<String>()
        val awayTeamName: LiveData<String>
            get() = _awayTeamName

        private var _gameDateTime = MutableLiveData<String>()
        val gameDateTime: LiveData<String>
            get() = _gameDateTime

        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?>
            get() = _errorMessage

        private val _navigateToLogin = MutableLiveData<Boolean>()
        val navigateToLogin: LiveData<Boolean>
            get() = _navigateToLogin

        private var _postBoardResponse = MutableLiveData<PostBoardResponse>()
        val postBoardResponse: LiveData<PostBoardResponse>
            get() = _postBoardResponse

        private var _putBoardResponse = MutableLiveData<PutBoardResponse>()
        val putBoardResponse: LiveData<PutBoardResponse>
            get() = _putBoardResponse

        fun setHomeTeamName(teamName: String) {
            _homeTeamName.value = teamName
        }

        fun setAwayTeamName(teamName: String) {
            _awayTeamName.value = teamName
        }

        fun setGameDate(gameDateTime: String) {
            _gameDateTime.value = gameDateTime
        }

        fun postBoard(postBoardRequest: PostBoardRequest) {
            viewModelScope.launch {
                val result = postBoardUseCase.postBoard(postBoardRequest)
                result
                    .onSuccess { response ->
                        _postBoardResponse.value = response
                    }.onFailure { exception ->
                        if (exception is ReissueFailureException) {
                            _navigateToLogin.value = true
                        } else {
                            _errorMessage.value = exception.message
                        }
                    }
            }
        }

        fun putBoard(putBoardRequest: PutBoardRequest) {
            viewModelScope.launch {
                val result = putBoardUseCase.putBoard(putBoardRequest)
                result
                    .onSuccess { response ->
                        _putBoardResponse.value = response
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
