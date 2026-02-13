package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine01Regular
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun OnboardingTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        modifier =
            modifier
                .fillMaxWidth(),
        style = HeadLine01Regular,
        color = Grey800,
    )
}



@Composable
@Preview
fun PreviewOnBoardingText() {
    OnboardingTitle(text = stringResource(R.string.tac_title_1))
}
