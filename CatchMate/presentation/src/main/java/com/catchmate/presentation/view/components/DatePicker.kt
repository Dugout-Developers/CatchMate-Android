package com.catchmate.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine03Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey100
import com.catchmate.presentation.view.theme.Grey300
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800
import java.time.LocalDate

@Composable
fun DateItem(
    date: LocalDate,
    isSelected: Boolean,
    isEnabled: Boolean, // 오늘 이전 날짜는 false로 전달
    onClick: () -> Unit
) {
    val today = LocalDate.now()
    val isToday = date == today
    
    val backgroundColor =
        when {
            isSelected -> Brand500
            isToday -> Grey100
            else -> Color.Transparent
        }

    val textColor =
        when {
            isSelected -> Grey0
            !isEnabled -> Grey300
            else -> Grey800
        }

    Box(
        modifier = Modifier
            .aspectRatio(1f) // 정사각형 유지
            .clip(CircleShape)
            .size(38.dp)
            .background(backgroundColor)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = Body01Medium,
            color = textColor
        )
    }
}

@Composable
fun HomeCalendarGrid(
    currentMonth: LocalDate,
    selectedDate: LocalDate,
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.dayOfWeek.value % 7
    val today = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 24.dp),
        ) {
            IconButton(
                onClick = {},//
            ) {
                Icon(
                    painter = painterResource(R.drawable.vec_calendar_before_triangle_20dp),
                    contentDescription = null,
                    tint = Grey300,
                )
            }
            Text(
                text = "월",//
                style = HeadLine03Medium,
                color = Grey800,
            )
            IconButton(
                onClick = {},//
            ) {
                Icon(
                    painter = painterResource(R.drawable.vec_calendar_triangle_next_20dp),
                    contentDescription = null,
                    tint = Grey300,
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Grey500,
                    style = Body02Medium,
                )
            }
        }
        Spacer(Modifier.height(14.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            items(firstDayOfWeek) { Spacer(Modifier.fillMaxSize()) }
            items(daysInMonth) { dayIndex ->
                val date = currentMonth.withDayOfMonth(dayIndex + 1)
                val isPast = date.isBefore(today)
                val isSelected = selectedDate == date

                DateItem(
                    date = date,
                    isSelected = isSelected,
                    isEnabled = !isPast,
                    onClick = {},
                )
            }
        }
        Spacer(Modifier.height(30.dp))
    }

}

@Composable
@Preview
fun PreviewHomeCalendarGrid() {
    HomeCalendarGrid(
        currentMonth = LocalDate.now().withDayOfMonth(1),
        selectedDate = LocalDate.parse("2026-02-28"),
    )
}

@Composable
@Preview
fun PreviewDateItem() {
    DateItem(
        date = LocalDate.parse("2026-02-01"),
        isSelected = false,
        isEnabled = false,
        onClick = {},
    )
}
