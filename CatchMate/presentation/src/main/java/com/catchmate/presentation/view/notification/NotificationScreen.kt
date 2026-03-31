package com.catchmate.presentation.view.notification

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.catchmate.domain.model.notification.NotificationInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.components.CatchMateTopAppBarText
import com.catchmate.presentation.view.components.CatchmateSwipeToDismiss
import com.catchmate.presentation.view.components.ListEmptyComponent
import com.catchmate.presentation.view.components.TopAppBarTextType
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.viewmodel.notification.NotificationEvent
import com.catchmate.presentation.viewmodel.notification.NotificationUiState

@Composable
fun NotificationScreen(
    uiState: NotificationUiState,
    onEvent: (NotificationEvent) -> Unit,
) {
    val listState = rememberLazyListState()

    val shouldLoadMore =
        remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
                lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 2
            }
        }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && uiState.hasNext) {
            onEvent(NotificationEvent.OnMoreListLoaded)
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            CatchMateTopAppBar(
                onBackClick = { onEvent(NotificationEvent.OnBackClicked) },
                title = {
                    CatchMateTopAppBarText(
                        text = stringResource(R.string.notification_title),
                        type = TopAppBarTextType.TITLE,
                        startPadding = 18,
                    )
                },
            )
        },
        containerColor = Grey0,
    ) { innerPadding ->
        when {
            uiState.notificationList == null -> {
                ListEmptyComponent(
                    iconRes = R.drawable.vec_all_list_error_icon,
                    titleText = stringResource(R.string.all_error_page_title),
                )
            }

            uiState.notificationList.isEmpty() -> {
                ListEmptyComponent(
                    iconRes = R.drawable.img_no_list_icon,
                    titleText = stringResource(R.string.notification_no_list_message),
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    state = listState,
                ) {
                    items(
                        items = uiState.notificationList,
                        key = { it.id },
                    ) { item ->
                        CatchmateSwipeToDismiss(
                            item = item,
                            onDelete = { onEvent(NotificationEvent.OnItemSwiped(it.id)) },
                            modifier = Modifier.animateItem(),
                        ) {
                            NotificationItemBox(
                                item = item,
                                onClick = { onEvent(NotificationEvent.OnItemClicked(it.id)) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewNotificationScreen() {
    NotificationScreen(
        NotificationUiState(
            listOf(
                NotificationInfo(
                    id = 1,
                    title = "망그러진곰님의 직관 신청이 도착했어요",
                    alarmType = "",
                    read = true,
                    createdAt = "",
                    senderProfileImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4Y8C0XSLa2fOCPgeLYf6Es2mQwmapOOIy7w&s",
                    gameInfo = "2026-05-15 · 대구 · 한화 이글스 vs 두산 베어스",
                ),
                NotificationInfo(
                    id = 2,
                    title = "직관 신청이 수락되었어요",
                    alarmType = "",
                    read = false,
                    createdAt = "",
                    senderProfileImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4Y8C0XSLa2fOCPgeLYf6Es2mQwmapOOIy7w&s",
                    gameInfo = "2026-05-15 · 대구 · 한화 이글스 vs 두산 베어스",
                ),
            )
        ),
        {},
    )
}
