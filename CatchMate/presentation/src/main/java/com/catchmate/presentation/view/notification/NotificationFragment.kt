package com.catchmate.presentation.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentNotificationBinding
import com.catchmate.presentation.interaction.OnNotificationItemClickListener
import com.catchmate.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    Fragment(),
    OnNotificationItemClickListener {
    private var _binding: FragmentNotificationBinding? = null
    val binding get() = _binding!!

    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initHeader()
        initViewModel()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutHeaderNotification.run {
            tvHeaderTextTitle.setText(R.string.notification_title)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initViewModel() {
        notificationViewModel.getReceivedNotificationList()
        notificationViewModel.receivedNotificationList.observe(viewLifecycleOwner) { response ->
            if (response.notificationInfoList.isEmpty()) {
                binding.rvNotificationList.visibility = View.GONE
                binding.layoutNotificationNoList.visibility = View.VISIBLE
            } else {
                binding.rvNotificationList.visibility = View.VISIBLE
                binding.layoutNotificationNoList.visibility = View.GONE
                val adapter = binding.rvNotificationList.adapter as NotificationAdapter
                adapter.updateNotificationList(response.notificationInfoList)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvNotificationList.apply {
            adapter = NotificationAdapter(requireContext(), layoutInflater, this@NotificationFragment)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onNotificationItemClick(
        notificationId: Long,
        currentPos: Int,
    ) {
        notificationViewModel.getReceivedNotification(notificationId)
        notificationViewModel.receivedNotification.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val adapter = binding.rvNotificationList.adapter as NotificationAdapter
                adapter.updateSelectedNotification(currentPos)
            }
        }
    }
}
