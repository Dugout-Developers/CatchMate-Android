package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.BoardListResponse
import com.catchmate.domain.usecase.BoardListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val boardListUseCase: BoardListUseCase,
    ) : ViewModel() {
        private val _boardListResponse = MutableLiveData<List<BoardListResponse>>()
        val boardListResponse: LiveData<List<BoardListResponse>>
            get() = _boardListResponse

        fun getBoardList(
            accessToken: String,
            pageNum: Long,
            gudans: String = "",
            people: Int = 0,
            gameDate: String = "9999-99-99",
        ) {
            viewModelScope.launch {
                _boardListResponse.value = boardListUseCase.getBoardList(accessToken, pageNum, gudans, people, gameDate)
            }
        }
    }
