package com.catchmate.presentation.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.CatchMateIcon
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body03Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey700

@Composable
fun HomeFilterChip(
    text: String,
    selectedData: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (selectedData.isBlank()) {
            Grey0
        } else {
            Grey50
        }

    val border =
        if (selectedData.isBlank()) {
            Modifier
        } else {
            Modifier.border(width = 1.dp, color = Brand500, shape = RoundedCornerShape(8.dp))
        }

    val tint =
        if (selectedData.isBlank()) {
            Grey700
        } else {
            Brand500
        }

    val realText =
        if (selectedData.isBlank()) {
            text
        } else {
            selectedData
        }

    Box(
        modifier =
            modifier
                .wrapContentSize(align = Alignment.Center)
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .clickable { onClick() }
                .then(border),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp).padding(start = 16.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = realText,
                style = Body03Medium,
                color = tint,
            )
            Spacer(Modifier.width(4.dp))
            CatchMateIcon(
                iconRes = R.drawable.vec_home_filter_dropdown_20dp,
                tint = tint,
            )
        }
    }
}

@Composable
@Preview
fun PreviewHomeFilterChip() {
    HomeFilterChip("경기 날짜", "03.21 수", {})
}
