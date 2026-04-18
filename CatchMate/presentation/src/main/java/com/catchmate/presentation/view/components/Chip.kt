package com.catchmate.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02SemiBold
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey400
import com.catchmate.presentation.view.theme.Grey50

@Composable
fun OnboardingGenderChip(
    text: String,
    isSelected: Boolean,
    onSelectedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) Grey0 else Grey50
    val strokeColor = if (isSelected) Brand500 else Color.Transparent
    val textColor = if (isSelected) Brand500 else Grey400

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = strokeColor,
                    shape = RoundedCornerShape(8.dp),
                ).clickable { onSelectedChange() }
                .padding(vertical = 17.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = Body02SemiBold,
            color = textColor,
        )
    }
}

@Composable
@Preview
fun PreviewCatchMateChip() {
    OnboardingGenderChip(stringResource(R.string.female), false, {})
}
