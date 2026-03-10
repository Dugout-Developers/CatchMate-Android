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
import androidx.compose.foundation.layout.width
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
import com.catchmate.presentation.view.components.CatchMateIcon
import com.catchmate.presentation.view.components.CatchMateIndicator
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine01Regular
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey800
import com.catchmate.presentation.viewmodel.onboarding.TeamOnboardingEvent
import com.catchmate.presentation.viewmodel.onboarding.TeamOnboardingUiState

@Composable
fun TeamOnboardingScreen(
    uiState: TeamOnboardingUiState,
    onEvent: (TeamOnboardingEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Grey0,
        topBar = {
            CatchMateTopAppBar(
                onBackClick = { onEvent(TeamOnboardingEvent.OnBackClicked) },
                actions = {
                    CatchMateIndicator(currentPage = 3)
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
                    onClick = { onEvent(TeamOnboardingEvent.OnSubmitClicked) },
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
                    .padding(top = 52.dp),
        ) {
            Text(
                text = stringResource(R.string.team_onboarding_title1, uiState.nickname),
                style = HeadLine01Regular,
                color = Grey800,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.team_onboarding_title2),
                    style = HeadLine01Regular,
                    color = Grey800,
                )
                Spacer(modifier = Modifier.width(6.dp))
                CatchMateIcon(
                    iconRes = R.drawable.vec_all_essential_mark_6dp,
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier =
                    Modifier
                        .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                items(12) { id ->
                    CatchMateTeamButton(
                        teamName = uiState.teamButtonTextList[id],
                        teamLogoRes = uiState.teamButtonLogoList[id],
                        isChecked = uiState.selectedClubId == (id + 1),
                        onCheckedChange = { onEvent(TeamOnboardingEvent.OnTeamSelected(id + 1)) }, // id 변수는 0부터, clubId는 1부터 시작
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewTeamOnboardingScreen() {
    TeamOnboardingScreen(
        uiState = TeamOnboardingUiState(),
        onEvent = {},
    )
}
