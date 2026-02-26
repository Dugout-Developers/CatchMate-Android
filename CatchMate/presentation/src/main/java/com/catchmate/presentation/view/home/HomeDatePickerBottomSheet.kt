package com.catchmate.presentation.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.catchmate.presentation.view.components.CatchMateBottomSheetButton
import com.catchmate.presentation.view.components.HomeCalendarGrid
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Opacity40
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDatePickerBottomSheet(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate?) -> Unit,
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
                .padding(bottom = 34.dp)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HomeCalendarGrid(
                currentMonth = LocalDate.now().withDayOfMonth(1),//
                selectedDate = LocalDate.parse("2026-02-28"),//
            )
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
fun PreviewHomeDatePickerBottomSheet() {
    HomeDatePickerBottomSheet(
        {},
        {},
    )
}
