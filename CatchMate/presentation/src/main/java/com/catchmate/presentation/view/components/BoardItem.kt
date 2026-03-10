package com.catchmate.presentation.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.catchmate.domain.model.board.Board
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.domain.model.enroll.UserInfo
import com.catchmate.domain.model.user.Club
import com.catchmate.presentation.R
import com.catchmate.presentation.util.DateUtils
import com.catchmate.presentation.util.ResourceUtil.convertTeamColor
import com.catchmate.presentation.util.ResourceUtil.convertTeamLogo
import com.catchmate.presentation.view.theme.Brand500
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body01Medium
import com.catchmate.presentation.view.theme.CatchMateTextStyle.Body03Medium
import com.catchmate.presentation.view.theme.Grey0
import com.catchmate.presentation.view.theme.Grey500
import com.catchmate.presentation.view.theme.Grey800

@Composable
fun BoardItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    board: Board,
    isChecked: Boolean? = null,
    onLiked: ((Boolean) -> Unit)? = null,
) {
    val dateTimePair = DateUtils.formatISODateTime(board.gameResponse.gameStartDate!!)
    val homeTeamLogo = convertTeamLogo(board.gameResponse.homeClub?.clubId!!)
    val homeTeamColor = convertTeamColor(board.gameResponse.homeClub?.clubId!!)
    val awayTeamLogo = convertTeamLogo(board.gameResponse.awayClub?.clubId!!)
    val awayTeamColor = convertTeamColor(board.gameResponse.awayClub?.clubId!!)
    val isCheerTeam = board.gameResponse.homeClub?.clubId!! == board.cheerClub.clubId

    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        color = Grey0,
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier =
                Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BoardMemberCountText(
                    currentMemberCount = board.currentPerson,
                    maxMemberCount = board.maxPerson,
                    modifier = Modifier.weight(1f),
                )
                if (isChecked != null && onLiked != null) {
                    CatchMateCheckBox(
                        isChecked = isChecked,
                        onCheckedChange = { onLiked(!isChecked) },
                        checkedIconRes = R.drawable.vec_all_liked_filled_24dp,
                        uncheckedIconRes = R.drawable.vec_all_liked_empty_24dp,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = "${dateTimePair.first} | ${dateTimePair.second} | ${board.gameResponse.location}",
                style = Body03Medium,
                color = Brand500,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = board.title,
                style = Body01Medium,
                color = Grey800,
            )
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CatchMateTeamLogoBox(
                    logoRes = homeTeamLogo,
                    teamColor = homeTeamColor,
                    isSelected = isCheerTeam,
                    isAlphaApplied = !isCheerTeam,
                    onClick = {},
                )
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(R.string.versus),
                    color = Grey500,
                    style = Body03Medium,
                )
                CatchMateTeamLogoBox(
                    logoRes = awayTeamLogo,
                    teamColor = awayTeamColor,
                    isSelected = !isCheerTeam,
                    isAlphaApplied = isCheerTeam,
                    onClick = {},
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewBoardItem() {
    val board =
        Board(
            boardId = 9007199254740991,
            title = "카리나 시구 보러 같이 가실 분",
            content = "로롤ㄹ로로로로롤로롤로롤로로롤로로롤로롤",
            currentPerson = 2,
            maxPerson = 6,
            bookMarked = false,
            cheerClub =
                Club(
                    clubId = 8,
                    name = "이글스",
                    homeStadium = "한화생명 이글스파크",
                    region = "대전",
                ),
            gameResponse =
                GameInfo(
                    gameId = 90071992,
                    gameStartDate = "2026-04-23T18:30:00",
                    location = "대전",
                    homeClub =
                        Club(
                            clubId = 8,
                            name = "이글스",
                            homeStadium = "한화생명 이글스파크",
                            region = "대전",
                        ),
                    awayClub =
                        Club(
                            clubId = 9,
                            name = "다이노스",
                            homeStadium = "창원 NC 파크",
                            region = "창원",
                        ),
                ),
            userResponse =
                UserInfo(
                    userId = 90071992,
                    nickName = "nickname",
                    email = "lge6716@gmail.com",
                    profileImageUrl = "",
                    gender = "F",
                    birthDate = "1999-04-23",
                    watchStyle = "감독",
                    club =
                        Club(
                            clubId = 8,
                            name = "이글스",
                            homeStadium = "한화생명 이글스파크",
                            region = "대전",
                        ),
                ),
        )
    BoardItem(
        onClick = {},
        board = board,
        isChecked = false,
        onLiked = { true },
    )
}
