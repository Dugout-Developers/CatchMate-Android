package com.catchmate.presentation.view.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun CatchMateIcon(
    modifier: Modifier = Modifier,
    iconRes: Int,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
) {
    Icon(
        painter = painterResource(iconRes),
        modifier =
            modifier
                .wrapContentWidth(),
        contentDescription = contentDescription,
        tint = tint,
    )
}
