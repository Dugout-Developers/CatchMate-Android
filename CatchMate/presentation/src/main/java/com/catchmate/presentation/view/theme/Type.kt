package com.catchmate.presentation.view.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.catchmate.presentation.R

val Pretendard =
    FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    )

// 1. 공통 기본 스타일 (XML의 style name="Typography" 역할)
private val baseTextStyle =
    TextStyle(
        fontFamily = Pretendard,
        color = Grey800, // 아까 만든 Color.kt의 색상
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    )

// 2. 각 Headline 및 Body 정의
val CatchMateTypography =
    Typography(
        headlineLarge =
            baseTextStyle.copy(
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = (-0.01).sp,
            ),

        headlineMedium =
            baseTextStyle.copy(
                fontSize = 24.sp,
                lineHeight = 31.sp,
            ),

        headlineSmall =
            baseTextStyle.copy(
                fontSize = 20.sp,
                lineHeight = 26.sp,
            ),

        bodyLarge =
            baseTextStyle.copy(
                fontSize = 16.sp,
                lineHeight = 21.sp,
            ),

        bodyMedium =
            baseTextStyle.copy(
                fontSize = 14.sp,
                lineHeight = 18.sp,
            ),

        bodySmall =
            baseTextStyle.copy(
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),

        labelSmall =
            baseTextStyle.copy(
                fontSize = 11.sp,
                lineHeight = 15.sp,
            ),
    )

object CatchMateTextStyle {
    val HeadLine01SemiBold =
        CatchMateTypography.headlineLarge.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val HeadLine01Medium =
        CatchMateTypography.headlineLarge.copy(
            fontWeight = FontWeight.Medium,
        )
    val HeadLine01Regular =
        CatchMateTypography.headlineLarge.copy(
            fontWeight = FontWeight.Normal,
        )

    val HeadLine02SemiBold =
        CatchMateTypography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val HeadLine02Medium =
        CatchMateTypography.headlineMedium.copy(
            fontWeight = FontWeight.Medium,
        )
    val HeadLine02Regular =
        CatchMateTypography.headlineMedium.copy(
            fontWeight = FontWeight.Normal,
        )

    val HeadLine03SemiBold =
        CatchMateTypography.headlineSmall.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val HeadLine03Medium =
        CatchMateTypography.headlineSmall.copy(
            fontWeight = FontWeight.Medium,
        )
    val HeadLine03Regular =
        CatchMateTypography.headlineSmall.copy(
            fontWeight = FontWeight.Normal,
        )

    val Body01SemiBold =
        CatchMateTypography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val Body01Medium =
        CatchMateTypography.bodyLarge.copy(
            fontWeight = FontWeight.Medium,
        )
    val Body01Regular =
        CatchMateTypography.bodyLarge.copy(
            fontWeight = FontWeight.Normal,
        )

    val Body02SemiBold =
        CatchMateTypography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val Body02Medium =
        CatchMateTypography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
        )
    val Body02Regular =
        CatchMateTypography.bodyMedium.copy(
            fontWeight = FontWeight.Normal,
        )

    val Body03SemiBold =
        CatchMateTypography.bodySmall.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val Body03Medium =
        CatchMateTypography.bodySmall.copy(
            fontWeight = FontWeight.Medium,
        )
    val Body03Regular =
        CatchMateTypography.bodySmall.copy(
            fontWeight = FontWeight.Normal,
        )

    val Caption01SemiBold =
        CatchMateTypography.labelSmall.copy(
            fontWeight = FontWeight.SemiBold,
        )
    val Caption01Medium =
        CatchMateTypography.labelSmall.copy(
            fontWeight = FontWeight.Medium,
        )
    val Caption01Regular =
        CatchMateTypography.labelSmall.copy(
            fontWeight = FontWeight.Normal,
        )
}
