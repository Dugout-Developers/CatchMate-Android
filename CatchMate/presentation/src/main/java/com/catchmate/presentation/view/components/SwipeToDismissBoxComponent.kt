package com.catchmate.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey300
import com.catchmate.presentation.view.theme.SystemRed

@Composable
fun <T> CatchmateSwipeToDismiss(
    item: T,
    onDelete: (T) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    val dismissState =
        rememberSwipeToDismissBoxState(
            // 얼만큼 스와이프 되어야 dismiss 되는지 조절(40%)
            positionalThreshold = { totalDistance -> totalDistance * 0.4f },
        )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(lerp(Grey300, SystemRed, dismissState.progress))
                            .padding(20.dp),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Text(
                        text = stringResource(R.string.dialog_button_delete),
                        color = Grey0,
                        style = Body02Medium,
                    )
                }
            }
        },
        content = {
            content(item)
        },
        onDismiss = { direction ->
            if (direction == SwipeToDismissBoxValue.EndToStart) {
                onDelete(item)
            }
        },
    )
}
