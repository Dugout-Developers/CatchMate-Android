package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02SemiBold
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey700

@Composable
fun CatchMateTeamButton(
    teamName: String,
    teamLogoRes: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val border =
        if (isChecked) {
            Modifier.border(1.dp, Brand500, RoundedCornerShape(8.dp))
        } else {
            Modifier
        }
    val containerColor =
        if (isChecked) {
            Grey0
        } else {
            Grey50
        }

    val contentColor =
        if (isChecked) {
            Brand500
        } else {
            Grey700
        }

    Box(
        modifier =
            modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(containerColor)
                .clickable { onCheckedChange(!isChecked) }
                .then(border),
    ) {
        Column(
            modifier =
                Modifier
                    .wrapContentSize()
                    .padding(horizontal = 7.dp)
                    .padding(top = 7.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier =
                    Modifier.size(95.dp),
                painter = painterResource(teamLogoRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = teamName,
                style = Body02SemiBold,
                color = contentColor,
            )
        }
    }
}

@Composable
@Preview
fun PreviewTeamButton() {
    CatchMateTeamButton("다이노스", R.drawable.img_pacifist_icon, false, {})
}
