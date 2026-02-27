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
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine01Regular
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800
import com.catchmate.presentation.viewmodel.onboarding.TermsAndConditionEvent
import com.catchmate.presentation.viewmodel.onboarding.TermsAndConditionUiState

@Composable
fun TermsAndConditionScreen(
    uiState: TermsAndConditionUiState,
    onEvent: (TermsAndConditionEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Grey0,
        topBar = {
            CatchMateTopAppBar(
                onBackClick = { onEvent(TermsAndConditionEvent.OnBackClicked) },
                actions = {
                    CatchMateIndicator(currentPage = 1)
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
                    onClick = { onEvent(TermsAndConditionEvent.OnSubmitClicked) },
                    enabled = uiState.isSubmitButtonEnable,
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
                    .padding(horizontal = 24.dp)
                    .padding(top = 52.dp)
        ) {
            Text(
                text = stringResource(R.string.tac_title_1),
                style = HeadLine01Regular,
                color = Grey800,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.tac_title_2),
                    style = HeadLine01Regular,
                    color = Grey800,
                )
                Spacer(modifier = Modifier.width(6.dp))
                CatchMateIcon(
                    iconRes = R.drawable.vec_all_essential_mark_6dp,
                )
            }
            Spacer(Modifier.height(39.dp))
            TermsAndConditionAllAgreementRow(
                isChecked = uiState.isAllAgreementChecked,
                onCheckedChange = { onEvent(TermsAndConditionEvent.OnAllAgreementToggled) },
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.tac_terms_title),
                style = Body02Medium,
                color = Grey500,
            )
            Spacer(Modifier.height(10.dp))
            TermsAndConditionCheckRow(
                text = stringResource(R.string.tac_content_1),
                isChecked = uiState.isServiceTermsChecked,
                onCheckedChange = { onEvent(TermsAndConditionEvent.OnServiceTermsToggled) },
                onDetailClick = { onEvent(TermsAndConditionEvent.OnServiceDetailClicked) },
            )
            TermsAndConditionCheckRow(
                text = stringResource(R.string.tac_content_2),
                isChecked = uiState.isPrivacyPolicyChecked,
                onCheckedChange = { onEvent(TermsAndConditionEvent.OnPrivacyPolicyToggled) },
                onDetailClick = { onEvent(TermsAndConditionEvent.OnPrivacyDetailClicked) },
            )
            TermsAndConditionCheckRow(
                text = stringResource(R.string.tac_content_3),
                isChecked = uiState.isMarketingPushChecked,
                onCheckedChange = { onEvent(TermsAndConditionEvent.OnMarketingPushToggled) },
                onDetailClick = { onEvent(TermsAndConditionEvent.OnMarketingDetailClicked) },
            )
        }
    }
}

@Composable
@Preview
fun PreviewTermsAndConditionScreen() {
    TermsAndConditionScreen(
        uiState = TermsAndConditionUiState(),
        onEvent = {}
    )
}
