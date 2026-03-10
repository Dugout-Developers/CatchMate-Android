package com.catchmate.presentation.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand100
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Caption01Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine03Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey700
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun CatchMateIndicator(
    modifier: Modifier = Modifier,
    totalPages: Int = 4,
    currentPage: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(totalPages) { index ->
            val dotColor =
                if (index + 1 == currentPage) {
                    Brand500
                } else {
                    Brand100
                }
            Canvas(
                modifier = modifier.size(6.dp),
            ) {
                drawCircle(color = dotColor)
            }
        }
    }
}

@Composable
fun CatchMateTopAppBarText(
    modifier: Modifier = Modifier,
    text: String,
    type: TopAppBarTextType,
    startPadding: Int,
) {
    Spacer(modifier.width(startPadding.dp))
    Text(
        text = text,
        style =
            when (type) {
                TopAppBarTextType.TITLE -> HeadLine03Medium
                TopAppBarTextType.CHATTING -> Body01Medium
                TopAppBarTextType.COUNT -> Caption01Medium
            },
        color =
            if (type == TopAppBarTextType.COUNT) {
                Grey500
            } else {
                Grey800
            },
        maxLines = 1,
    )
}

@Composable
fun CatchMateTopAppBarMenu(
    modifier: Modifier = Modifier,
    iconRes: Int,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(24.dp),
    ) {
        CatchMateIcon(
            iconRes = iconRes,
            tint = Grey700,
        )
    }
}

@Composable
fun CatchMateTopAppBarTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier =
            modifier
                .size(28.dp, 18.dp),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = text,
            style = CatchMateTextStyle.Body02Medium,
            color = Grey800,
        )
    }
}

@Composable
fun CatchMateTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    val backgroundColor =
        if (onBackClick == null && actions != null) {
            Grey50
        } else {
            Grey0
        }
    Row(
        modifier =
            modifier
                .padding(horizontal = 18.dp, vertical = 9.dp)
                .fillMaxWidth()
                .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        onBackClick?.let {
            CatchMateBackButton(onClick = onBackClick)
        }

        Box(
            modifier =
                Modifier
                    .weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            title?.invoke()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            actions?.invoke(this)
        }
    }
}

@Composable
@Preview
fun PreviewCatchMateTopAppBar() {
    Column {
        CatchMateTopAppBar(
            title = {
                CatchMateIcon(
                    iconRes = R.drawable.vec_home_logo_text,
                    modifier = Modifier.size(100.dp, 27.dp),
                    tint = Color.Unspecified,
                )
            },
            actions = {
                CatchMateTopAppBarMenu(iconRes = R.drawable.vec_home_notification_24dp, onClick = {})
            },
        )
    }
}
