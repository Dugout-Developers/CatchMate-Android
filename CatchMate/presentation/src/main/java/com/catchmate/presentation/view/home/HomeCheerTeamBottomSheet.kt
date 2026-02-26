package com.catchmate.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.util.ClubUtils.convertClubIdToName
import com.catchmate.presentation.util.ResourceUtil.convertTeamColor
import com.catchmate.presentation.util.ResourceUtil.convertTeamLogo
import com.catchmate.presentation.view.components.CatchMateBottomSheetButton
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Opacity40
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCheerTeamBottomSheet(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate?) -> Unit,
    teamIdList: List<Int>,
    selectedTeamId: List<Int>? = null,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Grey0,
        scrimColor = Opacity40,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        dragHandle = {
            Image(
                painter = painterResource(R.drawable.vec_all_bottom_sheet_handle_62dp),
                contentDescription = null,
                modifier = Modifier.padding(top = 8.dp),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 34.dp, top = 24.dp)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .heightIn(max = 400.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(teamIdList) { id ->
                    val isSelected = selectedTeamId?.contains(id) == true
                    val logo = convertTeamLogo(id)
                    val color = convertTeamColor(id)
                    val name = convertClubIdToName(id)
                    HomeCheerTeamCheckView(
                        logoRes = logo,
                        teamColor = color,
                        teamName = name,
                        isChecked = isSelected,
                    )
                    Spacer(Modifier.height(11.dp))
                }
            }
            Spacer(Modifier.height(28.dp))
            CatchMateBottomSheetButton(
                onResetClicked = {},//
                onSubmitClicked = {},//
                isSubmitEnable = true,//
            )
        }
    }
}

@Composable
@Preview
fun PreviewHomeCheerTeamBottomSheet() {
    HomeCheerTeamBottomSheet(
        {},
        {},
        listOf(1,2,3,4,5,6,7,8,9,10),
    )
}