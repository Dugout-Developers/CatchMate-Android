package com.catchmate.presentation.view.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun CatchMateTheme(
    content: @Composable () -> Unit,
) {
    val colorScheme = lightColorScheme(
        primary = Brand500,
        onPrimary = Grey0,
        background = Grey0,
        onBackground = Grey800,
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CatchMateTypography,
        content = content,
    )
}
