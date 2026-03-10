package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body03Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun CatchMateCheerStyleButton(
    title: String,
    description: String,
    res: Int,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val border =
        if (isChecked) {
            Modifier.border(1.dp, Brand500, RoundedCornerShape(8.dp))
        } else {
            Modifier
        }
    val containerColor =
        if (isChecked) {
            Grey0
        } else {
            Grey50
        }
    Surface(
        onClick = onCheckedChange,
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        modifier =
            modifier
                .width(168.dp)
                .height(200.dp)
                .then(border),
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Text(text = title, style = Body01Medium, color = Grey800)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = description, style = Body03Medium, color = Grey500)
            }

            Image(
                painter = painterResource(res),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(72.dp)
                        .align(Alignment.BottomEnd), // 오른쪽 아래로 밀기
            )
        }
    }
}

@Composable
@Preview
fun PreviewCatchMateCheerStyleButton() {
    CatchMateCheerStyleButton(
        "감독 스타일",
        "어쩌구 저쩌구",
        R.drawable.img_director_icon,
        true,
        {},
    )
}
