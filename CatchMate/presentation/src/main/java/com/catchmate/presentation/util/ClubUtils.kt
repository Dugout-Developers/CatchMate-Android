package com.catchmate.presentation.util

import com.catchmate.domain.model.enumclass.Club
import com.catchmate.presentation.R

object ClubUtils {
    fun convertClubNameToId(clubName: String): Int =
        when (clubName) {
            "타이거즈" -> Club.KIA.id
            "히어로즈" -> Club.SAMSUNG.id
            "트윈스" -> Club.LG.id
            "베어스" -> Club.DOOSAN.id
            "위즈" -> Club.KT.id
            "랜더스" -> Club.SSG.id
            "자이언츠" -> Club.LOTTE.id
            "이글스" -> Club.HANWHA.id
            "다이노스" -> Club.NC.id
            "히어로즈" -> Club.KIWOOM.id
            "평화주의자" -> Club.PACIFIST.id
            else -> Club.BEGINNER.id
        }
}
