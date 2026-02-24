package com.catchmate.presentation.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamOnboardingViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(TeamOnboardingUiState())
        val uiState = _uiState.asStateFlow()

        private val _sideEffect = Channel<TeamOnboardingSideEffect>()
        val sideEffect = _sideEffect.receiveAsFlow()

        fun onEvent(event: TeamOnboardingEvent) {
            when (event) {
                TeamOnboardingEvent.OnBackClicked -> sendSideEffect(TeamOnboardingSideEffect.NavigateToBack)
                TeamOnboardingEvent.OnSubmitClicked -> {
                    if (uiState.value.isSubmitButtonEnable) {
                        sendSideEffect(TeamOnboardingSideEffect.NavigateToNext(uiState.value.selectedClubId!!))
                    }
                }
                is TeamOnboardingEvent.OnTeamSelected -> {
                    _uiState.update {
                        it.copy(
                            selectedClubId =
                                if (uiState.value.selectedClubId == event.clubId){
                                    -1
                                } else {
                                    event.clubId
                                },
                            isSubmitButtonEnable = uiState.value.selectedClubId != event.clubId,
                        )
                    }
                }
            }
        }

        fun setTeamScreenData(
            nickname: String,
            logoList: List<Int>,
            textList: List<String>,
        ) {
            _uiState.update {
                it.copy(
                    nickname = nickname,
                    teamButtonLogoList = logoList,
                    teamButtonTextList = textList,
                )
            }
        }

        private fun sendSideEffect(effect: TeamOnboardingSideEffect) {
            viewModelScope.launch {
                _sideEffect.send(effect)
            }
        }
    }
