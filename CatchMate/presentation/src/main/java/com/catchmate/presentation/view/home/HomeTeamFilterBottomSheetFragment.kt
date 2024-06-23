package com.catchmate.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catchmate.presentation.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeTeamFilterBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_team_filter_bottom_sheet, container, false)
    }
    // 체크박스마다 setOnCheckedChangeListener 달아서 이미지뷰 리소스를 변경하기
}
