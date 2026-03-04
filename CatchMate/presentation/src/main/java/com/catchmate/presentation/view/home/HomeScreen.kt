package com.catchmate.presentation.view.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.domain.model.board.Board
import com.catchmate.presentation.R
import com.catchmate.presentation.util.ClubUtils.convertClubIdListToString
import com.catchmate.presentation.util.DateUtils.formatDateToFilterDate
import com.catchmate.presentation.view.components.BoardItem
import com.catchmate.presentation.view.components.CatchMateIcon
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.components.CatchMateTopAppBarMenu
import com.catchmate.presentation.view.components.ListEmptyComponent
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.viewmodel.home.HomeEvent
import com.catchmate.presentation.viewmodel.home.HomeUiState

@Composable
fun HomeScreen(
    boardList: List<Board>?,
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
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
            onEvent(HomeEvent.OnLoadMoreBoards)
        }
    }

    val clubText =
        if (uiState.clubFilterData.isNotEmpty()) {
            convertClubIdListToString(uiState.clubFilterData)
        } else {
            ""
        }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize(),
        topBar = {
            CatchMateTopAppBar(
                title = {
                    CatchMateIcon(
                        iconRes = R.drawable.vec_home_logo_text,
                        modifier = Modifier.size(100.dp, 27.dp),
                    )
                },
                actions = {
                    CatchMateTopAppBarMenu(
                        iconRes = R.drawable.vec_home_notification_24dp,
                        onClick = { onEvent(HomeEvent.OnNotificationClicked) },
                    )
                },
            )
        },
        containerColor = Grey50,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding),
        ) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                HomeFilterChip(
                    text = stringResource(R.string.home_filter_date),
                    selectedData = uiState.selectedDate?.let { formatDateToFilterDate(it) } ?: "",
                    onClick = { onEvent(HomeEvent.OnDateFilterClicked(uiState.selectedDate)) },
                )
                HomeFilterChip(
                    text = stringResource(R.string.home_filter_team),
                    selectedData = clubText,
                    onClick = { onEvent(HomeEvent.OnClubFilterClicked) },
                )
                HomeFilterChip(
                    text = stringResource(R.string.home_filter_member_count),
                    selectedData = uiState.memberFilterData,
                    onClick = { onEvent(HomeEvent.OnMemberFilterClicked) },
                )
            }
            when {
                boardList == null -> {
                    ListEmptyComponent(
                        iconRes = R.drawable.vec_all_list_error_icon,
                        titleText = stringResource(R.string.all_error_page_title),
                    )
                }
                boardList.isEmpty() -> {
                    ListEmptyComponent(
                        iconRes = R.drawable.img_no_list_icon,
                        titleText = stringResource(R.string.home_no_list_message),
                    )
                }
                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier,
                    ) {
                        items(boardList) { board ->
                            BoardItem(
                                onClick = { onEvent(HomeEvent.OnBoardItemClicked(board.boardId)) },
                                board = board,
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
fun PreviewHomeScreen() {
    HomeScreen(emptyList(), HomeUiState(), {})
}
