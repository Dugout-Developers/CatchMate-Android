package com.catchmate.presentation.view.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.presentation.R
import com.catchmate.presentation.view.components.CatchMateCheckBox
import com.catchmate.presentation.view.components.CatchMateIconButton
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01SemiBold
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body02Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.HeadLine01Regular
import com.catchmate.presentation.view.theme.Grey50
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey700
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun TermsAndConditionAllAgreementRow(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Grey50)
                .clickable { onCheckedChange(!isChecked) }
                .padding(horizontal = 16.dp, vertical = 15.5.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CatchMateCheckBox(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
                checkedIconRes = R.drawable.vec_all_check_btn_checked_24dp,
                uncheckedIconRes = R.drawable.vec_all_check_btn_unchecked_24dp,
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = stringResource(R.string.tac_all_check_title),
                style = Body01SemiBold,
                color = Grey800,
            )
        }
    }
}

@Composable
fun TermsAndConditionCheckRow(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onCheckedChange(!isChecked) }
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CatchMateCheckBox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
            checkedIconRes = R.drawable.vec_all_check_btn_checked_24dp,
            uncheckedIconRes = R.drawable.vec_all_check_btn_unchecked_24dp,
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            style = Body02Medium,
            color = Grey700,
            modifier = Modifier.weight(1f),
        )
        CatchMateIconButton(
            onClick = onDetailClick,
            modifier =
                Modifier
                    .size(20.dp),
            iconRes = R.drawable.vec_all_right_arrow_20dp,
            tint = Grey500,
        )
    }
}

@Composable
@Preview
fun PreviewTermsAndConditionText() {
    Column {
        Text(
            text = stringResource(R.string.tac_title_1),
            style = HeadLine01Regular,
            color = Grey800,
        )
        TermsAndConditionAllAgreementRow(true, {})
        TermsAndConditionCheckRow(
            stringResource(R.string.tac_content_1),
            true,
            {},
            {},
        )
    }
}
