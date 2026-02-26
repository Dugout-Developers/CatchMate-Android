package com.catchmate.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.HanwhaEagles

@Composable
fun CatchMateTeamLogoBox(
    modifier: Modifier = Modifier,
    logoRes: Int,
    teamColor: Color,
    isSelected: Boolean, // 배경색 구단색으로 칠할지 여부(응원팀 여부)
    isAlphaApplied: Boolean = false, // 로고 투명도 적용 여부 (게시글 용)
    isEnabled: Boolean = false, // 클릭 가능 여부 (바텀시트 용)
    onClick: () -> Unit,
) {
    val backgroundColor =
        if (isSelected) {
            teamColor
        } else {
            Grey50
        }

    // 게시글(isAlphaApplied=t)이면서 응원팀 아닐 때만 투명도 적용
    val logoAlpha =
        if (isAlphaApplied && !isSelected) {
            0.6f
        } else {
            1f
        }

    Box(
        modifier =
            modifier
                .size(50.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(backgroundColor)
                .then(
                    if (isEnabled) Modifier.clickable { onClick() } else Modifier
                ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(logoRes),
            contentDescription = null,
            alpha = logoAlpha,
            modifier = Modifier.size(45.83.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
@Preview
fun PreviewCatchMateTeamLogoBox() {
    CatchMateTeamLogoBox(
        logoRes = R.drawable.vec_all_hanwha_eagles_logo,
        teamColor = HanwhaEagles,
        isSelected = false,
        isAlphaApplied = false,
        onClick = {},
    )
}
