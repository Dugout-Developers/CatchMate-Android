package com.catchmate.presentation.view.support

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.databinding.FragmentAnnouncementDetailBinding
import com.catchmate.presentation.view.base.BaseFragment

class AnnouncementDetailFragment : BaseFragment<FragmentAnnouncementDetailBinding>(FragmentAnnouncementDetailBinding::inflate) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            layoutHeaderAnnouncementDetail.apply {
                tvHeaderTextTitle.visibility = View.GONE
                imgbtnHeaderTextBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }
}
