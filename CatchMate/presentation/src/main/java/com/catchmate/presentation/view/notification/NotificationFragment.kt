package com.catchmate.presentation.view.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.enumclass.AcceptState
import com.catchmate.domain.model.notification.NotificationInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentNotificationBinding
import com.catchmate.presentation.interaction.OnListItemAllRemovedListener
import com.catchmate.presentation.interaction.OnNotificationItemClickListener
import com.catchmate.presentation.interaction.OnNotificationItemSwipeListener
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.NotificationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate),
    OnNotificationItemClickListener,
    OnNotificationItemSwipeListener,
    OnListItemAllRemovedListener {
    private val notificationViewModel: NotificationViewModel by viewModels()

    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
    private var notificationList: MutableList<NotificationInfo> = mutableListOf()
    private var deletedItemPos: Int = -1
    private var clickedItemPos: Int = -1

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initHeader()
        initViewModel()
        initRecyclerView()

        if (isFirstLoad) {
            getNotificationList()
            isFirstLoad = false
        }
    }

    private fun initHeader() {
        binding.layoutHeaderNotification.run {
            tvHeaderTextTitle.setText(R.string.notification_title)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun getNotificationList() {
        if (isLoading || isLastPage) return
        isLoading = true
        notificationViewModel.getReceivedNotificationList(currentPage)
        isApiCalled = true
    }

    private fun initViewModel() {
        notificationViewModel.receivedNotificationList.observe(viewLifecycleOwner) { response ->
            if (response.isFirst && response.isLast && response.totalElements == 0) {
                binding.rvNotificationList.visibility = View.GONE
                binding.layoutNotificationNoList.visibility = View.VISIBLE
            } else {
                binding.rvNotificationList.visibility = View.VISIBLE
                binding.layoutNotificationNoList.visibility = View.GONE
                if (isApiCalled) {
                    notificationList.addAll(response.notificationInfoList)
                }
                val adapter = binding.rvNotificationList.adapter as NotificationAdapter
                adapter.updateNotificationList(notificationList)
                isLastPage = response.isLast
                isLoading = false
            }
            isApiCalled = false
        }
        notificationViewModel.deletedNotificationResponse.observe(viewLifecycleOwner) { response ->
            if (response.state) {
                val adapter = binding.rvNotificationList.adapter as NotificationAdapter
                adapter.removeItem(deletedItemPos)
            } else {
                Snackbar.make(requireView(), "해당 알림을 삭제할 수 없습니다. 잠시후 다시 시도해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
        notificationViewModel.receivedNotification.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val adapter = binding.rvNotificationList.adapter as NotificationAdapter
                adapter.updateSelectedNotification(clickedItemPos)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvNotificationList.apply {
            adapter =
                NotificationAdapter(
                    requireContext(),
                    layoutInflater,
                    this@NotificationFragment,
                    this@NotificationFragment,
                    this@NotificationFragment,
                )
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(
                        recyclerView: RecyclerView,
                        dx: Int,
                        dy: Int,
                    ) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager)
                                .findLastCompletelyVisibleItemPosition()
                        val itemTotalCount = recyclerView.adapter!!.itemCount
                        if (lastVisibleItemPosition + 1 >= itemTotalCount && !isLastPage && !isLoading) {
                            currentPage += 1
                            getNotificationList()
                        }
                    }
                },
            )
        }
        val itemTouchHelper =
            ItemTouchHelper(
                SwipeDeleteCallback(
                    requireContext(),
                    binding.rvNotificationList,
                    notificationList,
                ),
            )
        itemTouchHelper.attachToRecyclerView(binding.rvNotificationList)
    }

    override fun onNotificationItemClick(
        notificationId: Long,
        currentPos: Int,
        acceptStatus: String,
    ) {
        clickedItemPos = currentPos
        notificationViewModel.getReceivedNotification(notificationId)
        if (acceptStatus == AcceptState.PENDING.name) { // pending
            findNavController().navigate(R.id.action_notificationFragment_to_receivedJoinFragment)
        } else if (acceptStatus == AcceptState.ACCEPTED.name) { // accepted
            findNavController().navigate(R.id.action_notificationFragment_to_chattingRoomFragment)
        }
    }

    override fun onNotificationItemSwipe(
        pos: Int,
        notificationId: Long,
    ) {
        deletedItemPos = pos
        notificationViewModel.deleteNotification(notificationId)
    }

    override fun onListItemAllRemoved() {
        binding.apply {
            rvNotificationList.visibility = View.GONE
            layoutNotificationNoList.visibility = View.VISIBLE
        }
    }
}
