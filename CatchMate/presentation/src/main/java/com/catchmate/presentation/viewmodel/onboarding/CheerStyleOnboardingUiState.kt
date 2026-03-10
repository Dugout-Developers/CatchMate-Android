package com.catchmate.presentation.viewmodel.onboarding

data class CheerStyleOnboardingUiState(
    val selectedButtonId: Int? = null,
    val nickname: String = "",
    val cheerStyleButtonLogoList: List<Int> = emptyList(),
    val cheerStyleButtonTextList: List<Pair<String, String>> = emptyList(),
)
