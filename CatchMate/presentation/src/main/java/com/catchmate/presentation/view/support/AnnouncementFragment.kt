package com.catchmate.presentation.view.support

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentAnnouncementBinding
import com.catchmate.presentation.view.base.BaseFragment

class AnnouncementFragment : BaseFragment<FragmentAnnouncementBinding>(FragmentAnnouncementBinding::inflate) {
    private var announcementListAdapter: AnnouncementListAdapter? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        val announcementList =
            listOf(
                Pair(
                    "타이틀",
                    "콘텐츠",
                ),
            )
        announcementListAdapter?.submitList(announcementList)
    }

    private fun initView() {
        binding.apply {
            layoutHeaderAnnouncement.apply {
                tvHeaderTextTitle.setText(R.string.mypage_announcement)
                imgbtnHeaderTextBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
            announcementListAdapter = AnnouncementListAdapter()
            rvAnnouncement.apply {
                adapter = announcementListAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }
}
