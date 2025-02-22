package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentServiceCenterInquiryBinding
import com.catchmate.presentation.view.base.BaseFragment

class ServiceCenterInquiryFragment : BaseFragment<FragmentServiceCenterInquiryBinding>(FragmentServiceCenterInquiryBinding::inflate) {
    private val inquiryType by lazy { arguments?.getString("inquiryType") ?: "" }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            imgbtnBackServiceCenterInquiry.setOnClickListener {
                findNavController().popBackStack()
            }

            layoutFooterServiceCenterInquiry.btnFooterOne.apply {
                text = getString(R.string.service_center_inquiry_apply_btn)
            }
        }
    }
}
