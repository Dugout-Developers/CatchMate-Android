package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.OnboardingGenderChip

@Composable
fun SignupGenderChipGroup(
    selectedGender: String = "여성",
    onGenderSelect: (String) -> Unit,
) {
    val male = stringResource(R.string.male)
    val female = stringResource(R.string.female)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        OnboardingGenderChip(
            text = female,
            isSelected = selectedGender == female,
            onSelectedChange = { onGenderSelect(female) },
            modifier = Modifier.weight(1f),
        )
        OnboardingGenderChip(
            text = male,
            isSelected = selectedGender == male,
            onSelectedChange = { onGenderSelect(male) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
@Preview
fun PreviewSignupGenderChipGroup() {
    SignupGenderChipGroup("여성", {})
}
