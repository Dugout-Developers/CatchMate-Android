package com.catchmate.presentation.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand50
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle
import com.catchmate.presentation.view.theme.Grey0

@Composable
fun CatchMateFilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonType: ButtonType = ButtonType.FILLED,
) {
    val containerColor =
        when {
            buttonType == ButtonType.WEAK -> Grey0
            enabled -> Brand500
            else -> Brand50
        }

    val contentColor =
        when (buttonType) {
            ButtonType.WEAK -> Brand500
            else -> Grey0
        }

    val border =
        if (buttonType == ButtonType.WEAK) {
            BorderStroke(1.dp, Brand500)
        } else {
            null
        }

    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
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
            style = CatchMateTextStyle.Body01SemiBold,
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
@Preview
fun PreviewCatchMateFilledButton() {
    CatchMateFilledButton(
        text = "text",
        onClick = {},
        enabled = false,
        buttonType = ButtonType.WEAK,
    )
}
