package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingHomeBinding
import com.catchmate.presentation.interaction.OnChattingRoomSelectedListener
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ChattingHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingHomeFragment :
    BaseFragment<FragmentChattingHomeBinding>(FragmentChattingHomeBinding::inflate),
    OnChattingRoomSelectedListener {
    private val chattingHomeViewModel: ChattingHomeViewModel by viewModels()
    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
    private var chatRoomList: MutableList<ChatRoomInfo> = mutableListOf()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

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
                // 채팅 없을때 표시할 레이아웃 가시성 처리
                Log.e("NO CHATTING", "NO")
            } else {
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
                findNavController().navigate(R.id.action_chattingHomeFragment_to_loginFragment, null, navOptions)
            }
        }

        chattingHomeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvChattingHome.apply {
            adapter = ChattingRoomListAdapter(requireContext(), layoutInflater, this@ChattingHomeFragment)
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
    }

    private fun getChattingRoomList() {
        if (isLoading || isLastPage) return
        isLoading = true
        chattingHomeViewModel.getChattingRoomList(currentPage)
        isApiCalled = true
    }

    override fun onChattingRoomSelected(chatRoomId: Long) {
        val bundle = Bundle()
        bundle.putLong("chatRoomId", chatRoomId)
        findNavController().navigate(R.id.action_chattingHomeFragment_to_chattingRoomFragment, bundle)
    }
}
