package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.View
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentNotificationSettingBinding
import com.catchmate.presentation.view.base.BaseFragment

class NotificationSettingFragment : BaseFragment<FragmentNotificationSettingBinding>(FragmentNotificationSettingBinding::inflate) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
    }

    private fun initHeader() {
        binding.layoutHeaderNotificationSetting.apply {
            tvHeaderTextTitle.setText(R.string.mypage_setting_notification_setting)
        }
    }
}
