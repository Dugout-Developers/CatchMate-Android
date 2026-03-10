package com.catchmate.presentation.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R

@Composable
fun CatchMateCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    checkedIconRes: Int,
    uncheckedIconRes: Int,
    onCheckedChange: (Boolean) -> Unit,
) {
    val iconRes =
        if (isChecked) {
            checkedIconRes
        } else {
            uncheckedIconRes
        }

    Box(
        modifier =
            modifier
                .size(20.dp)
                .clip(CircleShape)
                .clickable { onCheckedChange(!isChecked) },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Composable
@Preview
fun PreviewCatchMateCheckBox() {
    CatchMateCheckBox(
        isChecked = true,
        onCheckedChange = {},
        checkedIconRes = R.drawable.vec_all_check_btn_checked_24dp,
        uncheckedIconRes = R.drawable.vec_all_check_btn_unchecked_24dp,
    )
}
