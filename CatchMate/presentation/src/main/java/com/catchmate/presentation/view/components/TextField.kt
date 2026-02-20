package com.catchmate.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02SemiBold
import com.catchmate.presentation.view.theme.Grey400
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun CatchMateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    showClearIcon: Boolean = true,
    onClearClick: () -> Unit = { onValueChange("") }
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val strokeColor = if (isFocused) Brand500 else Color.Transparent

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = Grey50, shape = RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = strokeColor,
                    shape = RoundedCornerShape(8.dp)
                ),
        textStyle = Body02Medium.copy(color = Grey800),
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Row(
                modifier =
                    Modifier
                        .padding(vertical = 17.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            style = Body02SemiBold,
                            color = Grey400,
                        )
                    }
                    innerTextField()
                }

                if (showClearIcon && isFocused && value.isNotEmpty()) {
                    CatchMateIconButton(
                        iconRes = R.drawable.vec_all_close_20dp,
                        onClick = onClearClick,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    )
}

@Composable
@Preview
fun PreviewCatchMateTextField() {
    CatchMateTextField("", {}, stringResource(R.string.signup_nickname_hint))
}
