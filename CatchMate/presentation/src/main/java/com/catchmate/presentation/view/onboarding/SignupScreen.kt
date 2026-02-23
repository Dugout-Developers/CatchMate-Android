package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.ButtonType
import com.catchmate.presentation.view.components.CatchMateFilledButton
import com.catchmate.presentation.view.components.CatchMateIcon
import com.catchmate.presentation.view.components.CatchMateIndicator
import com.catchmate.presentation.view.components.CatchMateTextField
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Caption01SemiBold
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine01Regular
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800
import com.catchmate.presentation.view.theme.SystemBlue
import com.catchmate.presentation.view.theme.SystemRed
import com.catchmate.presentation.viewmodel.onboarding.SignUpEvent
import com.catchmate.presentation.viewmodel.onboarding.SignUpUiState

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Grey0,
        topBar = {
            CatchMateTopAppBar(
                onBackClick = { onEvent(SignUpEvent.OnBackClicked) },
                actions = {
                    CatchMateIndicator(currentPage = 2)
                }
            )
        },
        bottomBar = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                CatchMateFilledButton(
                    text = stringResource(R.string.next),
                    onClick = { onEvent(SignUpEvent.OnSubmitClicked) },
                    enabled = uiState.isSubmitButtonEnable,
                    buttonType = ButtonType.FILLED,
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp),
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
                    .padding(horizontal = 24.dp)
                    .padding(top = 52.dp)
        ) {
            Text(
                text = stringResource(R.string.signup_title_1),
                style = HeadLine01Regular,
                color = Grey800,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.signup_title_2),
                    style = HeadLine01Regular,
                    color = Grey800,
                )
                Spacer(modifier = Modifier.width(6.dp))
                CatchMateIcon(
                    iconRes = R.drawable.vec_all_essential_mark_6dp,
                )
            }
            Spacer(Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.signup_nickname),
                    style = Body02Medium,
                    color = Grey500,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = uiState.nickname.length.toString() + stringResource(R.string.signup_nickname_count_limit),
                    style = Body02Medium,
                    color = Grey500,
                )
            }
            Spacer(Modifier.height(12.dp))
            CatchMateTextField(
                value = uiState.nickname,
                onValueChange = { onEvent(SignUpEvent.OnNicknameChanged(it)) },
                hint = stringResource(R.string.signup_nickname_hint),
            )
            Spacer(Modifier.height(4.dp))
            if (uiState.nickname.isNotEmpty()) {
                Text(
                    text = stringResource(
                        if (uiState.isNicknameValid == true) {
                            R.string.signup_nickname_usable
                        } else {
                            R.string.signup_nickname_unusable
                        }
                    ),
                    style = Caption01SemiBold,
                    color = if (uiState.isNicknameValid == true) SystemBlue else SystemRed,
                )
            }
            Spacer(Modifier.height(13.dp))
            Text(
                text = stringResource(R.string.signup_birth),
                style = Body02Medium,
                color = Grey500,
            )
            Spacer(Modifier.height(12.dp))
            CatchMateTextField(
                value = uiState.birthDate,
                onValueChange = { onEvent(SignUpEvent.OnBirthDateChanged(it)) },
                hint = stringResource(R.string.signup_birth_hint),
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.signup_gender),
                style = Body02Medium,
                color = Grey500,
            )
            Spacer(Modifier.height(12.dp))
            SignupGenderChipGroup(
                selectedGender = uiState.gender,
                onGenderSelect = { onEvent(SignUpEvent.OnGenderSelected(it)) },
            )
        }
    }
}

@Composable
@Preview
fun PreviewSignUpScreen() {
    SignUpScreen(
        uiState = SignUpUiState(),
        onEvent = {},
    )
}
