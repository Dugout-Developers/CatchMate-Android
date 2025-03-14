package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingHomeBinding
import com.catchmate.presentation.interaction.OnChattingRoomSelectedListener
import com.catchmate.presentation.interaction.OnItemSwipeListener
import com.catchmate.presentation.interaction.OnListItemAllRemovedListener
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ChattingHomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingHomeFragment :
    BaseFragment<FragmentChattingHomeBinding>(FragmentChattingHomeBinding::inflate),
    OnChattingRoomSelectedListener,
    OnItemSwipeListener,
    OnListItemAllRemovedListener {
    private val chattingHomeViewModel: ChattingHomeViewModel by viewModels()
    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
    private var deletedItemPos: Int = -1
    private var chatRoomList: MutableList<ChatRoomInfo> = mutableListOf()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        enableDoubleBackPressedExit = true

        setFragmentResultListener("deleteChattingRoomResultKey") { _, bundle ->
            val deletedChatRoomId = bundle.getLong("chatRoomId")
            deleteChatRoom(deletedChatRoomId)
        }

        initHeader()
        initViewModel()
        initRecyclerView()

        if (isFirstLoad) {
            getChattingRoomList()
            isFirstLoad = false
        }
    }

    private fun initHeader() {
        binding.layoutHeaderChattingHome.apply {
            tvHeaderTextTitle.setText(R.string.chatting_home_title)
            imgbtnHeaderTextBack.visibility = View.GONE
        }
    }

    private fun initViewModel() {
        chattingHomeViewModel.getChattingRoomListResponse.observe(viewLifecycleOwner) { response ->
            if (response.isFirst && response.isLast && response.totalElements == 0) {
                binding.layoutChattingHomeNoList.visibility = View.VISIBLE
                binding.rvChattingHome.visibility = View.GONE
            } else {
                binding.rvChattingHome.visibility = View.VISIBLE
                binding.layoutChattingHomeNoList.visibility = View.GONE
                Log.e("EXIST CHATTING", "EXIST")
                if (isApiCalled) {
                    chatRoomList.addAll(response.chatRoomInfoList)
                }
                val adapter = binding.rvChattingHome.adapter as ChattingRoomListAdapter
                adapter.updateList(chatRoomList)
                isLastPage = response.isLast
                isLoading = false
            }
            isApiCalled = false
        }
        chattingHomeViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.chattingHomeFragment, true)
                        .build()
                val bundle = Bundle()
                bundle.putInt("navigateCode", NAVIGATE_CODE_REISSUE)
                findNavController().navigate(R.id.action_chattingHomeFragment_to_loginFragment, bundle, navOptions)
            }
        }
        chattingHomeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
        chattingHomeViewModel.leaveChattingRoomResponse.observe(viewLifecycleOwner) { response ->
            if (response.state) {
                val adapter = binding.rvChattingHome.adapter as ChattingRoomListAdapter
                adapter.removeItem(deletedItemPos)
            } else {
                Snackbar.make(requireView(), "해당 채팅방을 나갈 수 없습니다. 잠시 후 다시 시도해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvChattingHome.apply {
            adapter =
                ChattingRoomListAdapter(
                    requireContext(),
                    layoutInflater,
                    this@ChattingHomeFragment,
                    this@ChattingHomeFragment,
                    this@ChattingHomeFragment,
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
                            getChattingRoomList()
                        }
                    }
                },
            )
        }
        val itemTouchHelper =
            ItemTouchHelper(
                SwipeChattingRoomCallback(
                    binding.rvChattingHome,
                    chatRoomList,
                ),
            )
        itemTouchHelper.attachToRecyclerView(binding.rvChattingHome)
    }

    private fun getChattingRoomList() {
        if (isLoading || isLastPage) return
        isLoading = true
        chattingHomeViewModel.getChattingRoomList(currentPage)
        isApiCalled = true
    }

    private fun deleteChatRoom(chatRoomId: Long) {
        chatRoomList = chatRoomList.filter { it.chatRoomId != chatRoomId }.toMutableList()
        val adapter = binding.rvChattingHome.adapter as ChattingRoomListAdapter
        adapter.updateList(chatRoomList)
    }

    override fun onChattingRoomSelected(chatRoomId: Long) {
        val bundle = Bundle()
        bundle.putLong("chatRoomId", chatRoomId)
        findNavController().navigate(R.id.action_chattingHomeFragment_to_chattingRoomFragment, bundle)
    }

    override fun onNotificationItemSwipe(
        position: Int,
        swipedItemId: Long,
    ) {
        deletedItemPos = position
        chattingHomeViewModel.leaveChattingRoom(swipedItemId)
    }

    override fun onListItemAllRemoved() {
        binding.layoutChattingHomeNoList.visibility = View.VISIBLE
        binding.rvChattingHome.visibility = View.GONE
    }
}
