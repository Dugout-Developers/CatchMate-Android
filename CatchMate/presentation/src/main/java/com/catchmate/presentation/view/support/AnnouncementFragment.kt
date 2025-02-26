package com.catchmate.presentation.view.support

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentAnnouncementBinding
import com.catchmate.presentation.interaction.OnAnnouncementItemClickListener
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.AnnouncementViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnnouncementFragment :
    BaseFragment<FragmentAnnouncementBinding>(FragmentAnnouncementBinding::inflate),
    OnAnnouncementItemClickListener {
    private val announcementViewModel: AnnouncementViewModel by viewModels()
    private var announcementListAdapter: AnnouncementListAdapter? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        announcementViewModel.getNoticeList()
        initView()
    }

    private fun initView() {
        binding.apply {
            layoutHeaderAnnouncement.apply {
                tvHeaderTextTitle.setText(R.string.mypage_announcement)
                imgbtnHeaderTextBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
            announcementListAdapter = AnnouncementListAdapter(this@AnnouncementFragment)
            rvAnnouncement.apply {
                adapter = announcementListAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun initViewModel() {
        announcementViewModel.getNoticeListResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                announcementListAdapter?.submitList(response.noticeInfoList)
            }
        }
    }

    override fun onAnnouncementItemClick(noticeId: Long) {
        val bundle = Bundle()
        bundle.putLong("noticeId", noticeId)
        findNavController().navigate(R.id.action_announcementFragment_to_announcementDetailFragment, bundle)
    }
}
