package com.catchmate.presentation.view.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.catchmate.domain.model.board.Board
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.BoardItem
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.components.CatchMateTopAppBarText
import com.catchmate.presentation.view.components.ListEmptyComponent
import com.catchmate.presentation.view.components.TopAppBarTextType
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.viewmodel.favorite.FavoriteEvent
import com.catchmate.presentation.viewmodel.favorite.FavoriteUiState

@Composable
fun FavoriteScreen(
    boardList: List<Board>?,
    uiState: FavoriteUiState,
    onEvent: (FavoriteEvent) -> Unit,
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
            onEvent(FavoriteEvent.OnLoadMoreBoards)
        }
    }

    Scaffold(
        modifier =
            Modifier.fillMaxSize(),
        topBar = {
            CatchMateTopAppBar(
                title = {
                    CatchMateTopAppBarText(
                        text = stringResource(R.string.favorite_title),
                        type = TopAppBarTextType.TITLE,
                        startPadding = 18,
                    )
                }
            )
        },
        containerColor = Grey0,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 18.dp),
        ) {
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
                        titleText = stringResource(R.string.favorite_no_list_title),
                        descriptionText = stringResource(R.string.favorite_no_list_explain)
                    )
                }
                else -> {
                    Spacer(Modifier.height(16.dp))
                    LazyColumn(
                        state = listState,
                    ) {
                        items(
                            items = boardList,
                            key = { it.boardId },
                        ) { board ->
                            BoardItem(
                                modifier = Modifier.animateItem(),
                                onClick = { onEvent(FavoriteEvent.OnFavoriteBoardClicked(board.boardId)) },
                                board = board,
                                isChecked = board.bookMarked,
                                onLiked = { onEvent(FavoriteEvent.OnFavoriteBoardUnliked(board.boardId)) },
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
fun PreviewFavoriteScreen() {
    FavoriteScreen(emptyList(), FavoriteUiState(), {})
}
