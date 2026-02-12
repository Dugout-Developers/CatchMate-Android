package com.catchmate.presentation.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Grey0

@Composable
fun LoginScreen(
    onKakaoLoginClick: () -> Unit,
    onNaverLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onGuestLoginClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Grey0,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(R.drawable.vec_colored_logo),
                contentDescription = null,
                modifier = Modifier.size(57.dp, 71.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            LoginButtonGroup(onKakaoLoginClick, onNaverLoginClick, onGoogleLoginClick)
            Spacer(modifier = Modifier.weight(1f))
            GuestLoginButton(onGuestLoginClick)
            Spacer(modifier = Modifier.height(68.dp))
        }
    }
}

@Composable
@Preview
fun PreviewLoginScreen() {
    LoginScreen({}, {}, {}, {})
}
