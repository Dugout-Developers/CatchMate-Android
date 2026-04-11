package com.catchmate.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand50
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
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
fun RequiredTextRow(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.wrapContentSize(),
    ) {
        Text(
            text = text,
            style = Body02Medium,
            color = Grey500,
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            painter = painterResource(R.drawable.vec_all_essential_mark_6dp),
            contentDescription = null,
            tint = Brand500,
            modifier = Modifier.size(6.dp),
        )
    }
}

@Composable
@Preview
fun PreviewBoardMemberCountText() {
    Column {
        BoardMemberCountText(
            currentMemberCount = 4,
            maxMemberCount = 4,
        )
        RequiredTextRow(
            text = "기본 정보",
        )
    }
}
