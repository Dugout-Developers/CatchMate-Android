package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.ButtonType
import com.catchmate.presentation.view.components.CatchMateFilledButton
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine03SemiBold
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun SignUpCompleteScreen(onSubmitClicked: () -> Unit) {
    Scaffold(
        modifier =
            Modifier.fillMaxSize(),
        containerColor = Grey0,
        bottomBar = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                CatchMateFilledButton(
                    text = stringResource(R.string.finish),
                    onClick = onSubmitClicked,
                    enabled = true,
                    buttonType = ButtonType.FILLED,
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                )
                Spacer(Modifier.height(34.dp))
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 192.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.img_signup_complete_icon),
                contentDescription = null,
                modifier = Modifier.size(88.dp),
            )
            Spacer(Modifier.height(48.dp))
            Text(
                text = stringResource(R.string.signup_complete_title),
                style = HeadLine03SemiBold,
                color = Grey800,
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.signup_complete_sub_title),
                style = Body02Medium,
                color = Grey500,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
fun PreviewSignUpCompleteScreen() {
    SignUpCompleteScreen({})
}
