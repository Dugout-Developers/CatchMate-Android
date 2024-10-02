package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.BoardWriteRequest
import com.catchmate.domain.model.BoardWriteResponse
import com.catchmate.domain.usecase.BoardWriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel
    @Inject
    constructor(
        private val boardWriteUseCase: BoardWriteUseCase,
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

        private var _boardWriteResponse = MutableLiveData<BoardWriteResponse>()
        val boardWriteResponse: LiveData<BoardWriteResponse>
            get() = _boardWriteResponse

        fun setHomeTeamName(teamName: String) {
            _homeTeamName.value = teamName
        }

        fun setAwayTeamName(teamName: String) {
            _awayTeamName.value = teamName
        }

        fun setGameDate(gameDateTime: String) {
            _gameDateTime.value = gameDateTime
        }

        fun postBoardWrite(boardWriteRequest: BoardWriteRequest) {
            viewModelScope.launch {
                _boardWriteResponse.value = boardWriteUseCase.postBoardWrite(boardWriteRequest)
            }
        }
    }
