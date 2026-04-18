package com.catchmate.presentation.view.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02SemiBold
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey400
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun BoardGenderAgeChip(
    text: String,
    isSelected: Boolean = false,
    onSelectedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) Brand500 else Grey50
    val textColor = if (isSelected) Grey0 else Grey500

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .clickable { onSelectedChange() }
                .padding(vertical = 8.dp, horizontal = 16.dp),
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
fun BoardTextBox(
    text: String,
    hint: String,
    isMemberCountBox: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = if (text.isBlank()) Body02SemiBold else Body02Medium
    val textColor = if (text.isBlank()) Grey400 else Grey800
    val realText = if (text.isBlank()) hint else text

    Box(
        modifier =
            modifier
                .background(color = Grey50, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 17.dp, horizontal = 16.dp)
                .clickable { onClick() },
        contentAlignment = Alignment.CenterStart,
    ) {
        Row {
            Text(
                text = realText,
                style = textStyle,
                color = textColor,
                modifier = Modifier.weight(1f),
            )
            if (isMemberCountBox) {
                Text(
                    text = stringResource(R.string.post_edt_hint_count_unit),
                    style = Body02SemiBold,
                    color = Grey400,
                )
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    Column {
        BoardGenderAgeChip(stringResource(R.string.regardless_of_gender), true, {})
        BoardTextBox("", "본인 포함 인원", false, {})
    }
}
