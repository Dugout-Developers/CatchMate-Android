package com.catchmate.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catchmate.domain.model.BoardReadResponse
import com.catchmate.domain.usecase.BoardReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadPostViewModel
    @Inject
    constructor(
        private val boardReadUseCase: BoardReadUseCase,
    ) : ViewModel() {
        private var _boardReadResponse = MutableLiveData<BoardReadResponse>()
        val boardReadResponse: LiveData<BoardReadResponse>
            get() = _boardReadResponse

        fun getBoard(
            accessToken: String,
            boardId: Long,
        ) {
            viewModelScope.launch {
                _boardReadResponse.value = boardReadUseCase.getBoard(accessToken, boardId)
            }
        }
    }
