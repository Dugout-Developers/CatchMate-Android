package com.catchmate.presentation.util

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.catchmate.presentation.R

object ResourceUtil {
    fun convertTeamLogo(teamName: String): Int =
        when (teamName) {
            "다이노스" -> R.drawable.vec_all_nc_dinos_logo
            "라이온즈" -> R.drawable.vec_all_samsung_lions_logo
            "랜더스" -> R.drawable.vec_all_ssg_landers_logo
            "베어스" -> R.drawable.vec_all_doosan_bears_logo
            "위즈" -> R.drawable.vec_all_kt_wiz_logo
            "이글스" -> R.drawable.vec_all_hanwha_eagles_logo
            "자이언츠" -> R.drawable.vec_all_lotte_giants_logo
            "타이거즈" -> R.drawable.vec_all_kia_tigers_logo
            "트윈스" -> R.drawable.vec_all_lg_twins_logo
            else -> R.drawable.vec_all_kiwoom_heroes_logo
        }

    fun convertTeamColor(
        context: Context,
        teamName: String,
        isCheerTeam: Boolean,
        currentPage: String,
    ): Int =
        if (isCheerTeam) {
            when (teamName) {
                "다이노스" -> ContextCompat.getColor(context, R.color.nc_dinos)
                "라이온즈" -> ContextCompat.getColor(context, R.color.samsung_lions)
                "랜더스" -> ContextCompat.getColor(context, R.color.ssg_landers)
                "베어스" -> ContextCompat.getColor(context, R.color.doosan_bears)
                "위즈" -> ContextCompat.getColor(context, R.color.kt_wiz)
                "이글스" -> ContextCompat.getColor(context, R.color.hanwha_eagles)
                "자이언츠" -> ContextCompat.getColor(context, R.color.lotte_giants)
                "타이거즈" -> ContextCompat.getColor(context, R.color.kia_tigers)
                "트윈스" -> ContextCompat.getColor(context, R.color.lg_twins)
                else -> ContextCompat.getColor(context, R.color.kiwoom_heroes)
            }
        } else {
            if (currentPage == "home") {
                ContextCompat.getColor(context, R.color.grey50)
            } else {
                ContextCompat.getColor(context, R.color.grey0)
            }
        }

    fun setTeamLogoOpacity(
        imageView: ImageView,
        isCheerTeam: Boolean,
    ) {
        if (!isCheerTeam) {
            imageView.alpha = 0.6f
        } else {
            imageView.alpha = 1.0f
        }
    }

    fun setTeamViewResources(
        teamName: String,
        isCheerTeam: Boolean,
        backgroundView: ImageView,
        logoView: ImageView,
        currentPage: String,
        context: Context,
    ) {
        // 로고 설정
        logoView.setImageResource(convertTeamLogo(teamName))
        setTeamLogoOpacity(logoView, isCheerTeam)

        // 배경색 설정
        DrawableCompat.setTint(backgroundView.background, convertTeamColor(context, teamName, isCheerTeam, currentPage))
    }
}
