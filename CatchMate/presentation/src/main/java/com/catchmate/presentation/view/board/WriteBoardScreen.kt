package com.catchmate.presentation.view.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.domain.model.enumclass.AgeGroup
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.ButtonType
import com.catchmate.presentation.view.components.CatchMateFilledButton
import com.catchmate.presentation.view.components.CatchMateTextField
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.components.CatchMateTopAppBarTextButton
import com.catchmate.presentation.view.components.RequiredTextRow
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey500

@Composable
fun WriteBoardScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CatchMateTopAppBar(
                onBackClick = {}, //
                actions = {
                    CatchMateTopAppBarTextButton(
                        text = stringResource(R.string.temporary_storage),
                        onClick = {}, //
                    )
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
                    text = stringResource(R.string.post_complete),
                    onClick = { }, //
                    enabled = false, //
                    buttonType = ButtonType.FILLED,
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                )
                Spacer(Modifier.height(34.dp))
            }
        },
        containerColor = Grey0,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 18.dp)
                    .verticalScroll(scrollState),
        ) {
            Spacer(Modifier.height(20.dp))
            RequiredTextRow(text = stringResource(R.string.post_basic_info))
            Spacer(Modifier.height(12.dp))
            CatchMateTextField(
                value = "",
                onValueChange = {},
                hint = stringResource(R.string.post_edt_hint_title),
                showClearIcon = false,
            )
            Spacer(Modifier.height(8.dp))
            BoardTextBox(
                text = "", //
                hint = stringResource(R.string.post_edt_hint_count),
                isMemberCountBox = true,
                onClick = {}, //
            )
            Spacer(Modifier.height(32.dp))
            RequiredTextRow(text = stringResource(R.string.post_game_info))
            Spacer(Modifier.height(12.dp))
            BoardTextBox(
                text = "", //
                hint = stringResource(R.string.post_edt_hint_game_date_time),
                onClick = {}, //
            )
            Spacer(Modifier.height(12.dp))
            Row {
                BoardTextBox(
                    text = "", //
                    hint = stringResource(R.string.post_edt_hint_home),
                    onClick = {}, //
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(9.dp))
                BoardTextBox(
                    text = "", //
                    hint = stringResource(R.string.post_edt_hint_away),
                    onClick = {}, //
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(Modifier.height(12.dp))
            BoardTextBox(
                text = "", //
                hint = stringResource(R.string.post_edt_hint_cheer_team),
                onClick = {}, //
            )
            Spacer(Modifier.height(12.dp))
            BoardTextBox(
                text = "", //
                hint = stringResource(R.string.post_edt_hint_place),
                onClick = {}, //
            )
            Spacer(Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                RequiredTextRow(
                    text = stringResource(R.string.post_additional_info),
                )
                Text(
                    text = "0/300", //
                    style = Body02Medium,
                    color = Grey500,
                )
            }
            Spacer(Modifier.height(12.dp))
            CatchMateTextField(
                value = "",
                onValueChange = {}, //
                hint = stringResource(R.string.post_edt_hint_additional_info),
                showClearIcon = false,
                modifier = Modifier.height(170.dp),
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.post_preference_gender),
                style = Body02Medium,
                color = Grey500,
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BoardGenderAgeChip(
                    text = stringResource(R.string.regardless_of_gender),
                    onSelectedChange = {}, //
                )
                BoardGenderAgeChip(
                    text = stringResource(R.string.male),
                    onSelectedChange = {}, //
                )
                BoardGenderAgeChip(
                    text = stringResource(R.string.female),
                    onSelectedChange = {}, //
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.post_preference_age),
                style = Body02Medium,
                color = Grey500,
            )
            Spacer(Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 5,
            ) {
                AgeGroup.entries.forEach { age ->
                    BoardGenderAgeChip(
                        text = age.label,
                        onSelectedChange = {}, //
                    )
                }
            }
            Spacer(Modifier.height(56.dp))
        }
    }
}

@Composable
@Preview
fun PreviewWriteBoardScreen() {
    WriteBoardScreen()
}
