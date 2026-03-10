package com.catchmate.presentation.viewmodel.onboarding

data class TeamOnboardingUiState(
    val isSubmitButtonEnable: Boolean = false,
    val selectedClubId: Int? = null,
    val nickname: String = "",
    val teamButtonLogoList: List<Int> = emptyList(),
    val teamButtonTextList: List<String> = emptyList(),
)
