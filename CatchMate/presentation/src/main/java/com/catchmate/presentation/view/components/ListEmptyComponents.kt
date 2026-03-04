package com.catchmate.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine03SemiBold
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun ListEmptyComponent(
    modifier: Modifier = Modifier,
    iconRes: Int,
    titleText: String,
    descriptionText: String? = null,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(88.dp),
        )
        Spacer(Modifier.height(48.dp))
        Text(
            text = titleText,
            style = HeadLine03SemiBold,
            color = Grey800,
            textAlign = TextAlign.Center,
        )
        if (descriptionText != null) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = descriptionText,
                style = Body02Medium,
                color = Grey500,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
fun PreviewListEmptyComponent() {
    ListEmptyComponent(
        iconRes = R.drawable.img_no_list_icon,
        titleText = stringResource(R.string.home_no_list_message),
        descriptionText = "야구 팬들이 올린 다양한 글을 둘러보고\n마음에 드는 직관 글을 저장해보세요!"
    )
}
