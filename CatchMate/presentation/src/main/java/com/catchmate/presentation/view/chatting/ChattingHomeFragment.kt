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
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingHomeBinding
import com.catchmate.presentation.databinding.LayoutAlertDialogBinding
import com.catchmate.presentation.interaction.OnChattingRoomSelectedListener
import com.catchmate.presentation.interaction.OnItemSwipeListener
import com.catchmate.presentation.interaction.OnListItemAllRemovedListener
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.activity.MainActivity
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ChattingHomeViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
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
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
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
        localDataViewModel.getAccessToken()
        initHeader()
        initRecyclerView()
        initViewModel()

        if (isFirstLoad) {
            getChattingRoomList()
            isFirstLoad = false
        }
        (requireActivity() as MainActivity).refreshNotificationStatus()
    }

    override fun onResume() {
        super.onResume()
        // 화면이 다시 보일 때만 새로고침 (최초 실행 시에는 제외)
        if (!isFirstLoad) {
            Log.d("ChattingHomeFragment", "onResume: 채팅방 목록 새로고침")
            // 페이지 초기화
            currentPage = 0
            isLastPage = false
            isLoading = false

            chattingRoomListAdapter.submitList(emptyList())
            // 채팅방 목록 새로 불러오기
            chattingHomeViewModel.getChattingRoomList(currentPage)
        }
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
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { token ->
            chattingHomeViewModel.connectToWebSocket(token)
        }
        chattingHomeViewModel.getChattingRoomListResponse.observe(viewLifecycleOwner) { response ->
            if (response.isFirst && response.isLast && response.totalElements == 0) {
                binding.layoutChattingHomeNoList.visibility = View.VISIBLE
                binding.rvChattingHome.visibility = View.GONE
            } else {
                binding.rvChattingHome.visibility = View.VISIBLE
                binding.layoutChattingHomeNoList.visibility = View.GONE
                Log.e("EXIST CHATTING", "EXIST")
                // 새로고침 상태이거나 currentPage가 0이면 리스트를 새로 설정
                if (isApiCalled) {
                    if (currentPage == 0) {
                        // 새 리스트로 교체
                        chattingRoomListAdapter.submitList(response.chatRoomInfoList)
                    } else {
                        // 페이징 시 기존 리스트에 추가
                        val currentList = chattingRoomListAdapter.currentList.toMutableList()
                        currentList.addAll(response.chatRoomInfoList)
                        chattingRoomListAdapter.submitList(currentList)
                    }
                    isApiCalled = false
                } else {
                    chattingRoomListAdapter.submitList(response.chatRoomInfoList)
                }
                isLastPage = response.isLast
                isLoading = false
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
                Log.e("Reissue Error", it)
            }
        }
        chattingHomeViewModel.leaveChattingRoomResponse.observe(viewLifecycleOwner) { response ->
            if (response.state) {
                chattingRoomListAdapter.removeItem(deletedItemPos)
            } else {
                Snackbar.make(requireView(), "해당 채팅방을 나갈 수 없습니다. 잠시 후 다시 시도해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
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

    override fun onChattingRoomSelected(
        chatRoomId: Long,
        isNewChatRoom: Boolean,
    ) {
        val bundle = Bundle()
        bundle.putLong("chatRoomId", chatRoomId)
        findNavController().navigate(R.id.action_chattingHomeFragment_to_chattingRoomFragment, bundle)
        if (isNewChatRoom) {
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
