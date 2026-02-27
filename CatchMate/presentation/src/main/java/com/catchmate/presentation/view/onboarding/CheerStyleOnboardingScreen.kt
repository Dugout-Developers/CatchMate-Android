package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.catchmate.presentation.view.components.CatchMateIndicator
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Caption01SemiBold
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine01Regular
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800
import com.catchmate.presentation.viewmodel.onboarding.CheerStyleOnboardingEvent
import com.catchmate.presentation.viewmodel.onboarding.CheerStyleOnboardingUiState

@Composable
fun CheerStyleOnboardingScreen(
    uiState: CheerStyleOnboardingUiState,
    onEvent: (CheerStyleOnboardingEvent) -> Unit,
) {
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize(),
        containerColor = Grey0,
        topBar = {
            CatchMateTopAppBar(
                onBackClick = { onEvent(CheerStyleOnboardingEvent.OnBackClicked) },
                actions = {
                    CatchMateIndicator(currentPage = 4)
                },
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
                    onClick = { onEvent(CheerStyleOnboardingEvent.OnSubmitClicked) },
                    enabled = true,
                    buttonType = ButtonType.FILLED,
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                )
                Spacer(Modifier.height(34.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
                    .padding(top = 52.dp),
        ) {
            Text(
                text = stringResource(R.string.team_onboarding_title1, uiState.nickname),
                style = HeadLine01Regular,
                color = Grey800,
            )
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = stringResource(R.string.cheer_style_onboarding_title2),
                    style = HeadLine01Regular,
                    color = Grey800,
                )
                Text(
                    text = stringResource(R.string.selectable_mark),
                    style = Caption01SemiBold,
                    color = Grey500,
                    modifier = Modifier.padding(start = 6.dp, bottom = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier =
                    Modifier
                        .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                items(6) { id ->
                    CatchMateCheerStyleButton(
                        title = uiState.cheerStyleButtonTextList[id].first,
                        description = uiState.cheerStyleButtonTextList[id].second,
                        res = uiState.cheerStyleButtonLogoList[id],
                        isChecked = uiState.selectedButtonId == id,
                        onCheckedChange = { onEvent(CheerStyleOnboardingEvent.OnCheerStyleSelected(id)) },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewCheerStyleOnboardingScreen() {
    CheerStyleOnboardingScreen(
        uiState = CheerStyleOnboardingUiState(),
        onEvent = {},
    )
}
