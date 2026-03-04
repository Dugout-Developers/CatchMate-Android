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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.catchmate.presentation.view.components.FilterSheetType
import com.catchmate.presentation.view.components.HomeCalendarGrid
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Opacity40
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    sheetType: FilterSheetType,
    onDismissRequest: () -> Unit,
    onDateApply: (LocalDate) -> Unit,
    onClubApply: (List<Int>) -> Unit,
    onMemberApply: (String) -> Unit,
    onReset: (FilterSheetType) -> Unit,
) {
    // 임시 상태(초기값은 부모로부터 받은 initial 값)
    var tempDate by remember(sheetType) {
        mutableStateOf(if (sheetType is FilterSheetType.Date && sheetType.initialDate != null) sheetType.initialDate else null)
    }
    var tempClubIds by remember(sheetType) {
        mutableStateOf(if (sheetType is FilterSheetType.Club && sheetType.initialClubIds.isNotEmpty()) sheetType.initialClubIds.toSet() else emptySet())
    }
    var tempMemberCount by remember(sheetType) {
        mutableStateOf(if (sheetType is FilterSheetType.Member && sheetType.initialCount.isNotEmpty()) sheetType.initialCount else "")
    }

    var currentMonth by remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
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
            when (sheetType) {
                is FilterSheetType.Club -> {
                    val teamIdList =
                        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                            .heightIn(max = 400.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(teamIdList) { id ->
                            val isChecked = tempClubIds.contains(id)
                            val logo = convertTeamLogo(id)
                            val color = convertTeamColor(id)
                            val name = convertClubIdToName(id)
                            HomeCheerTeamCheckView(
                                logoRes = logo,
                                teamColor = color,
                                teamName = name,
                                isChecked = isChecked,
                                onClick = { nextState ->
                                    tempClubIds =
                                        if (nextState) {
                                            tempClubIds + id
                                        } else {
                                            tempClubIds - id
                                        }
                                }
                            )
                            Spacer(Modifier.height(11.dp))
                        }
                    }
                }
                is FilterSheetType.Date -> {
                    HomeCalendarGrid(
                        currentMonth = currentMonth,
                        selectedDate = tempDate,
                        onDateClick = { tempDate = it },
                        onMonthChange = { offset ->
                            currentMonth = currentMonth.plusMonths(offset.toLong())
                        }
                    )
                }
                is FilterSheetType.Member -> TODO()
            }
            Spacer(Modifier.height(28.dp))
            CatchMateBottomSheetButton(
                onResetClicked = {
                    onReset(sheetType)
                    onDismissRequest()
                },
                onSubmitClicked = {
                    when (sheetType) {
                        is FilterSheetType.Club -> onClubApply(tempClubIds.toList())
                        is FilterSheetType.Date -> tempDate?.let { onDateApply(it) }
                        is FilterSheetType.Member -> onMemberApply(tempMemberCount)
                    }
                    onDismissRequest()
                },
                isSubmitEnable =
                    when (sheetType) {
                        is FilterSheetType.Club -> tempClubIds.isNotEmpty()
                        is FilterSheetType.Date -> tempDate != null
                        is FilterSheetType.Member -> tempMemberCount.isBlank()
                    },
            )
        }
    }
}

@Composable
@Preview
fun PreviewHomeBottomSheet() {
//    HomeBottomSheet(FilterSheetType.Date(LocalDate.now().withDayOfMonth(1).toString()), {}, {}, {}, {}, {})
//    HomeBottomSheet(FilterSheetType.Club(listOf(1, 2)), {}, {}, {}, {}, {})
}
