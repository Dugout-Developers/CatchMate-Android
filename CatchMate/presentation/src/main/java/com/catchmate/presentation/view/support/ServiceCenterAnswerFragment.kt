package com.catchmate.presentation.view.support

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentServiceCenterAnswerBinding
import com.catchmate.presentation.view.base.BaseFragment

class ServiceCenterAnswerFragment : BaseFragment<FragmentServiceCenterAnswerBinding>(FragmentServiceCenterAnswerBinding::inflate) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            layoutHeaderServiceCenterAnswer.tvHeaderTextTitle.text = getString(R.string.notification_title)
            layoutHeaderServiceCenterAnswer.imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val title = getString(R.string.service_center_answer_title)
            tvTitleServiceCenterAnswer.text = title.format("nickname")
//            tvDateServiceCenterAnswer.text =
//            tvInquiryContentServiceCenterAnswer.text =
//            tvAnswerContentServiceCenterAnswer.text =
        }
    }
}
