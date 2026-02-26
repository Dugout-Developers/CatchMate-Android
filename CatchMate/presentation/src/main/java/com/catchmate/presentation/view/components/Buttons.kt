package com.catchmate.presentation.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand50
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01SemiBold
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02SemiBold
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500

@Composable
fun CatchMateFilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonType: ButtonType = ButtonType.FILLED,
) {
    val containerColor =
        when (buttonType) {
            ButtonType.WEAK -> Grey0
            ButtonType.RESET -> Grey50
            else -> {
                if (enabled){
                    Brand500
                } else {
                    Brand50
                }
            }
        }

    val contentColor =
        when (buttonType) {
            ButtonType.WEAK -> Brand500
            ButtonType.RESET -> Grey500
            else -> Grey0
        }

    val border =
        if (buttonType == ButtonType.WEAK) {
            BorderStroke(1.dp, Brand500)
        } else {
            null
        }

    val textStyle =
        if (buttonType == ButtonType.RESET) {
            Body02SemiBold
        } else {
            Body01SemiBold
        }

    Button(
        onClick = onClick,
        modifier =
            modifier
                .height(52.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor,
        ),
        border = border,
        contentPadding = PaddingValues(0.dp),
        elevation = null
    ) {
        Text(
            text = text,
            style = textStyle,
            color = contentColor
        )
    }
}

@Composable
fun CatchMateBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier =
            modifier
                .size(20.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.vec_all_left_arrow_20dp),
            contentDescription = null,
        )
    }
}

@Composable
fun CatchMateIconButton(
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
) {
    IconButton(
        onClick = onClick,
        modifier =
            modifier
                .size(24.dp),
    ) {
        CatchMateIcon(
            iconRes = iconRes,
            tint = tint,
        )
    }
}

@Composable
fun CatchMateBottomSheetButton(
    onResetClicked: () -> Unit,
    onSubmitClicked: () -> Unit,
    isSubmitEnable: Boolean,
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        CatchMateFilledButton(
            text = stringResource(R.string.reset),
            onClick = onResetClicked,
            enabled = true,
            buttonType = ButtonType.RESET,
            modifier = Modifier.width(67.dp),
        )
        Spacer(Modifier.width(9.dp))
        CatchMateFilledButton(
            text = stringResource(R.string.application),
            onClick = onSubmitClicked,
            enabled = isSubmitEnable,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
@Preview
fun PreviewCatchMateButtons() {
    Column {
        CatchMateFilledButton(
            text = "text",
            onClick = {},
            enabled = false,
            buttonType = ButtonType.WEAK,
        )
        CatchMateBottomSheetButton(
            {},
            {},
            true,
        )
    }
}
