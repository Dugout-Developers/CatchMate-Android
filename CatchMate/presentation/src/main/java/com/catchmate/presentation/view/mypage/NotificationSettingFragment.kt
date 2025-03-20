package com.catchmate.presentation.view.mypage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.enumclass.AlarmType
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentNotificationSettingBinding
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.activity.MainActivity
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.NotificationSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationSettingFragment : BaseFragment<FragmentNotificationSettingBinding>(FragmentNotificationSettingBinding::inflate) {
    private val allAlarm by lazy { arguments?.getString("allAlarm") ?: "" }
    private val chatAlarm by lazy { arguments?.getString("chatAlarm") ?: "" }
    private val enrollAlarm by lazy { arguments?.getString("enrollAlarm") ?: "" }
    private val eventAlarm by lazy { arguments?.getString("eventAlarm") ?: "" }
    private val notificationSettingViewModel: NotificationSettingViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val mainActivity = requireActivity() as MainActivity
            val permission = Manifest.permission.POST_NOTIFICATIONS

            if (mainActivity.checkSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED) {
                Log.e("알림 권한 상태", "허용됨")
                initViewModel()
                initView()
            } else {
                Log.e("알림 권한 상태", "거부됨")
                mainActivity.showPermissionRationaleDialog(
                    onCancelled = {
                        findNavController().popBackStack()
                    }
                )
            }
        }
    }

    private fun initView() {
        binding.apply {
            layoutHeaderNotificationSetting.apply {
                tvHeaderTextTitle.setText(R.string.mypage_setting_notification_setting)
                imgbtnHeaderTextBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
            switchNotificationSettingAll.isChecked = allAlarm == "Y"
            switchNotificationSettingChatting.isChecked = chatAlarm == "Y"
            switchNotificationSettingJoin.isChecked = enrollAlarm == "Y"
            switchNotificationSettingEvent.isChecked = eventAlarm == "Y"
            switchNotificationSettingAll.setOnCheckedChangeListener { buttonView, isChecked ->
                val isEnabled = if (isChecked) "Y" else "N"
                notificationSettingViewModel.patchUserAlarm(AlarmType.ALL.name, isEnabled)
            }
            switchNotificationSettingChatting.setOnCheckedChangeListener { buttonView, isChecked ->
                val isEnabled = if (isChecked) "Y" else "N"
                notificationSettingViewModel.patchUserAlarm(AlarmType.CHAT.name, isEnabled)
            }
            switchNotificationSettingJoin.setOnCheckedChangeListener { buttonView, isChecked ->
                val isEnabled = if (isChecked) "Y" else "N"
                notificationSettingViewModel.patchUserAlarm(AlarmType.ENROLL.name, isEnabled)
            }
            switchNotificationSettingEvent.setOnCheckedChangeListener { buttonView, isChecked ->
                val isEnabled = if (isChecked) "Y" else "N"
                notificationSettingViewModel.patchUserAlarm(AlarmType.EVENT.name, isEnabled)
            }
        }
    }

    private fun initViewModel() {
        notificationSettingViewModel.patchUserAlarmResponse.observe(viewLifecycleOwner) { reponse ->
            reponse?.let {
                Log.e("설정완료", "${reponse.userId} / ${reponse.alarmType} / ${reponse.isEnabled}")
            }
        }
        notificationSettingViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.notificationSettingFragment, true)
                        .build()
                val bundle = Bundle()
                bundle.putInt("navigateCode", NAVIGATE_CODE_REISSUE)
                findNavController().navigate(R.id.action_notificationSettingFragment_to_loginFragment, bundle, navOptions)
            }
        }
        notificationSettingViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
    }
}
