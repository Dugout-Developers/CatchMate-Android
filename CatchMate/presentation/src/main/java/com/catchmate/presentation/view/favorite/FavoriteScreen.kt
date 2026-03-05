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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.domain.model.board.Board
import com.catchmate.presentation.view.components.CatchMateTopAppBar
import com.catchmate.presentation.view.components.CatchMateTopAppBarText
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.BoardItem
import com.catchmate.presentation.view.components.TopAppBarTextType
import com.catchmate.presentation.view.theme.Grey0

@Composable
fun FavoriteScreen(
    boardList: List<Board>,
) {
    val listState = rememberLazyListState()

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
            modifier = Modifier.padding(innerPadding),
        ) {
            Spacer(Modifier.height(16.dp))
            LazyColumn(
                state = listState,
            ) {
                items(boardList) { board ->
                    BoardItem(
                        onClick = {  },//
                        board = board,
                        isChecked = board.bookMarked,
                        onLiked = {},//
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewFavoriteScreen() {
    FavoriteScreen(emptyList())
}
