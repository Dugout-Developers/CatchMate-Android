package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.enroll.EnrollInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentSentJoinBinding
import com.catchmate.presentation.interaction.OnPostItemClickListener
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.SentJoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentJoinFragment :
    BaseFragment<FragmentSentJoinBinding>(FragmentSentJoinBinding::inflate),
    OnPostItemClickListener {
    private val sentJoinViewModel: SentJoinViewModel by viewModels()

    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
    private var enrollList: MutableList<EnrollInfo> = mutableListOf()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initViewModel()
        initRecyclerView()

        if (isFirstLoad) {
            getRequestedEnrollList()
            isFirstLoad = false
        }
    }

    private fun initHeader() {
        binding.layoutSentJoinHeader.apply {
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvHeaderTextTitle.text = getString(R.string.mypage_sent_join)
        }
    }

    private fun initRecyclerView() {
        binding.rvSentJoinPostList.apply {
            adapter = SentJoinAdapter(requireContext(), layoutInflater, this@SentJoinFragment)
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
                            getRequestedEnrollList()
                        }
                    }
                },
            )
        }
    }

    private fun initViewModel() {
        sentJoinViewModel.requestedEnrollList.observe(viewLifecycleOwner) { response ->
            if (response.isFirst && response.isLast && response.totalElements == 0) {
                // 목록 없을때 layout 표시
            } else {
                if (isApiCalled) {
                    enrollList.addAll(response.enrollInfoList)
                }
                val adapter = binding.rvSentJoinPostList.adapter as SentJoinAdapter
                adapter.updateList(enrollList)
                isLastPage = response.isLast
                isLoading = false
            }
            isApiCalled = false
        }
    }

    private fun getRequestedEnrollList() {
        if (isLoading || isLastPage) return
        isLoading = true
        sentJoinViewModel.getRequestedEnrollList(currentPage)
        isApiCalled = true
    }

    override fun onPostItemClicked(boardId: Long) {
        val bundle = Bundle()
        bundle.putLong("boardId", boardId)
        findNavController().navigate(R.id.action_sentJoinFragment_to_readPostFragment, bundle)
    }
}
