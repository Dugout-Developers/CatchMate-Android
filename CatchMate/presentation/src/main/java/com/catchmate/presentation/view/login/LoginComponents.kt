package com.catchmate.presentation.view.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.CatchMateTextStyle
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey100
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey900
import com.catchmate.presentation.view.theme.SystemYellow

@Composable
fun KakaoLoginButton(onKakaoLoginClick: () -> Unit) {
    Button(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(50.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = SystemYellow,
            ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onKakaoLoginClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.vec_login_kakao),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.login_kakao),
                style = CatchMateTextStyle.Body02Medium,
                color = Grey900,
            )
        }
    }
}

@Composable
fun RoundLoginButton(
    iconRes: Int,
    onRoundLoginClick: () -> Unit,
) {
    Button(
        modifier =
            Modifier
                .size(53.dp),
        shape = CircleShape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Grey0,
            ),
        border = BorderStroke(1.dp, Grey100),
        onClick = onRoundLoginClick,
        contentPadding = PaddingValues(0.dp),
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
fun GuestLoginButton(onGuestLoginClick: () -> Unit) {
    Text(
        text = stringResource(R.string.login_sneak_peek),
        textDecoration = TextDecoration.Underline,
        style = CatchMateTextStyle.Body03Medium,
        color = Grey500,
        modifier =
            Modifier
                .clickable {
                    onGuestLoginClick()
                }.padding(8.dp),
    )
}

@Composable
fun LoginButtonGroup(
    onKakaoLoginClick: () -> Unit,
    onNaverLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.vec_login_tip),
            contentDescription = null,
            modifier =
                Modifier
                    .padding(bottom = 8.dp),
        )
        KakaoLoginButton(onKakaoLoginClick)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding(top = 24.dp, bottom = 16.dp),
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Grey100)
            Text(
                text = stringResource(R.string.login_divider_text),
                style = CatchMateTextStyle.Body03Medium,
                color = Grey500,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Grey100)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundLoginButton(R.drawable.vec_login_naver, onNaverLoginClick)
            VerticalDivider(modifier = Modifier.height(16.dp).padding(horizontal = 24.dp), color = Grey100)
            RoundLoginButton(R.drawable.vec_login_google, onGoogleLoginClick)
        }
    }
}

@Composable
@Preview
fun PreviewKakaoLoginButton() {
    KakaoLoginButton { }
}

@Composable
@Preview
fun PreviewLoginButtonGroup() {
    LoginButtonGroup({ }, { }, { })
}
