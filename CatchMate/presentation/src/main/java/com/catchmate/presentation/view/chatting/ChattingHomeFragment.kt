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
import com.bumptech.glide.Glide
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingHomeBinding
import com.catchmate.presentation.databinding.LayoutAlertDialogBinding
import com.catchmate.presentation.interaction.OnChattingRoomSelectedListener
import com.catchmate.presentation.interaction.OnItemSwipeListener
import com.catchmate.presentation.interaction.OnListItemAllRemovedListener
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ChattingHomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
    private var hasNext = true
    private var isLoading = false
    private var deletedItemPos: Int = -1
    private lateinit var chattingRoomListAdapter: ChattingRoomListAdapter

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
        initRecyclerView()
        initViewModel()
//        (requireActivity() as MainActivity).refreshNotificationStatus()
    }

    override fun onResume() {
        super.onResume()
        Log.d("ChattingHomeFragment", "onResume: 채팅방 목록 새로고침")
        // 페이지 초기화
        currentPage = 0
        hasNext = true
        isLoading = false

        chattingRoomListAdapter.submitList(emptyList())
        // 채팅방 목록 새로 불러오기
        getChattingRoomList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chattingHomeViewModel.topic?.dispose()
        chattingHomeViewModel.stompClient?.disconnect()
    }

    private fun initHeader() {
        binding.layoutHeaderChattingHome.apply {
            tvHeaderTextTitle.setText(R.string.chatting_home_title)
            imgbtnHeaderTextBack.visibility = View.GONE
        }
    }

    private fun initViewModel() {
        chattingHomeViewModel.getChattingRoomListResponse.observe(viewLifecycleOwner) { response ->
            isLoading = false
            if (!response.hasNext && response.totalElements == 0) {
                binding.layoutChattingHomeNoList.visibility = View.VISIBLE
                binding.rvChattingHome.visibility = View.GONE
            } else {
                binding.rvChattingHome.visibility = View.VISIBLE
                binding.layoutChattingHomeNoList.visibility = View.GONE
                if (currentPage == 0) {
                    // 새 리스트로 교체
                    chattingRoomListAdapter.submitList(response.content)
                } else {
                    // 페이징 시 기존 리스트에 추가
                    val currentList = chattingRoomListAdapter.currentList.toMutableList()
                    currentList.addAll(response.content)
                    chattingRoomListAdapter.submitList(currentList)
                }
                hasNext = response.hasNext
                Log.i("API 응답", "${response.hasNext}, ${response.totalElements}, $currentPage")
            }
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
                if (it == "ListLoadError") {
                    binding.rvChattingHome.visibility = View.GONE
                    binding.layoutChattingHomeNoList.visibility = View.VISIBLE
                    Glide
                        .with(requireContext())
                        .load(R.drawable.vec_all_list_error_icon)
                        .into(binding.ivChattingHomeNoList)
                    binding.tvChattingHomeNoList.setText(R.string.all_error_page_title)
                    binding.tvChattingHomeNoListContent.visibility = View.GONE
                } else {
                    Snackbar.make(requireView(), R.string.chatting_leave_room_fail, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        chattingHomeViewModel.leaveChattingRoomResponse.observe(viewLifecycleOwner) { response ->
            Log.i("채팅방 나가기 성공", "$response")
            chattingRoomListAdapter.removeItem(deletedItemPos)
        }
    }

    private fun initRecyclerView() {
        chattingRoomListAdapter = ChattingRoomListAdapter(this@ChattingHomeFragment, this@ChattingHomeFragment, this@ChattingHomeFragment)
        binding.rvChattingHome.apply {
            adapter = chattingRoomListAdapter
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
                        if (lastVisibleItemPosition + 1 >= itemTotalCount && hasNext && !isLoading) {
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
                ),
            )
        itemTouchHelper.attachToRecyclerView(binding.rvChattingHome)
    }

    private fun getChattingRoomList() {
        if (isLoading || !hasNext) return
        isLoading = true
        chattingHomeViewModel.getChattingRoomList(currentPage)
    }

    private fun deleteChatRoom(chatRoomId: Long) {
        val newList = chattingRoomListAdapter.currentList.filter { it.chatRoomId != chatRoomId }.toMutableList()
        chattingRoomListAdapter.submitList(newList)
    }

    private fun showChattingSystemAlertDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutAlertDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.apply {
            tvAlertDialogTitle.setText(R.string.chatting_system_alert_title)
            tvAlertDialogPositive.apply {
                setText(R.string.complete)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    override fun onChattingRoomSelected(chatRoomInfo: ChatRoomInfo) {
        val bundle = Bundle()
        bundle.putParcelable("chatRoomInfo", chatRoomInfo)
        findNavController().navigate(R.id.action_chattingHomeFragment_to_chattingRoomFragment, bundle)
        if (chatRoomInfo.lastMessage == null) {
            showChattingSystemAlertDialog()
        }
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
