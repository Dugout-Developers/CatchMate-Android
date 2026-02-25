package com.catchmate.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    isCheerTeam: Boolean,
) {
    val backgroundColor =
        if (isCheerTeam) {
            teamColor
        } else {
            Grey50
        }

    val logoAlpha =
        if (isCheerTeam) {
            1f
        } else {
            0.6f
        }

    Box(
        modifier =
            modifier
                .size(50.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(logoRes),
            contentDescription = null,
            alpha = logoAlpha,
            modifier = Modifier.size(45.83.dp),
        )
    }
}

@Composable
@Preview
fun PreviewCatchMateTeamLogoBox() {
    CatchMateTeamLogoBox(
        logoRes = R.drawable.vec_all_hanwha_eagles_logo,
        teamColor = HanwhaEagles,
        isCheerTeam = false,
    )
}
