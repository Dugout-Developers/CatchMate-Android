package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentMyPageSettingBinding
import com.catchmate.presentation.view.base.BaseFragment

class MyPageSettingFragment : BaseFragment<FragmentMyPageSettingBinding>(FragmentMyPageSettingBinding::inflate) {
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = arguments?.getString("email") ?: ""
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initMenu()
    }

    private fun initHeader() {
        binding.layoutHeaderMyPageSetting.apply {
            tvHeaderTextTitle.setText(R.string.mypage_setting)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initMenu() {
        binding.apply {
            tvMyPageSettingUserInfo.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("email", email)
                findNavController().navigate(R.id.action_myPageSettingFragment_to_accountInfoFragment, bundle)
            }
            tvMyPageSettingInformation.setOnClickListener {
                findNavController().navigate(R.id.action_myPageSettingFragment_to_informationFragment)
            }
        }

    }
}
