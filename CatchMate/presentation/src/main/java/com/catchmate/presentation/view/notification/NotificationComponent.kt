package com.catchmate.presentation.view.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.catchmate.domain.model.notification.NotificationInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.util.DateUtils.formatNotificationGameInfo
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02SemiBold
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun NotificationItemBox(
    modifier: Modifier = Modifier,
    item: NotificationInfo,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val backgroundColor = if (item.read) Grey50 else Grey0
    val titleColor = if (item.read) Grey500 else Grey800
    // 우선 직관 신청 알림 항목 표시하는 경우만 작성(api 수정 필요)
    val triple = formatNotificationGameInfo(item.gameInfo)
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .clickable { onClick() }
                .padding(horizontal = 18.dp, vertical = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(context)
                        .data(item.senderProfileImageUrl ?: R.drawable.ic_notification_samsung_device)
                        .crossfade(true)
                        .placeholder(R.drawable.vec_profile_placeholder)
                        .error(R.drawable.ic_notification_samsung_device)
                        .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(48.dp).clip(CircleShape),
            )
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                Text(
                    text = item.title,
                    style = Body01Medium,
                    color = titleColor,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${triple.first} | ${triple.second} | ${triple.third}",
                    style = Body02SemiBold,
                    color = Grey500,
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewNotificationItemBox() {
    NotificationItemBox(
        item =
            NotificationInfo(
                id = 1,
                title = "망그러진곰님의 직관 신청이 도착했어요",
                alarmType = "",
                read = false,
                createdAt = "",
                senderProfileImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4Y8C0XSLa2fOCPgeLYf6Es2mQwmapOOIy7w&s",
                gameInfo = "2026-05-15 · 대구 · 한화 이글스 vs 두산 베어스",
            ),
        onClick = {},
    )
}
