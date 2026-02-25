package com.catchmate.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.view.theme.Brand50
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Caption01Medium
import com.catchmate.presentation.view.theme.Grey100
import com.catchmate.presentation.view.theme.Grey500

@Composable
fun BoardMemberCountText(
    modifier: Modifier = Modifier,
    currentMemberCount: Int,
    maxMemberCount: Int,
) {
    val backgroundColor =
        if (currentMemberCount == maxMemberCount) {
            Grey100
        } else {
            Brand50
        }

    val text =
        if (currentMemberCount == maxMemberCount) {
            "$currentMemberCount/$maxMemberCount 마감"
        } else {
            "$currentMemberCount/$maxMemberCount"
        }

    val textColor =
        if (currentMemberCount == maxMemberCount) {
            Grey500
        } else {
            Brand500
        }

    Box(
        modifier =
            modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(horizontal = 8.dp, vertical = 2.dp),
    ) {
        Text(
            text = text,
            color = textColor,
            style = Caption01Medium,
        )
    }
}

@Composable
@Preview
fun PreviewBoardMemberCountText() {
    BoardMemberCountText(
        currentMemberCount = 4,
        maxMemberCount = 4,
    )
}
