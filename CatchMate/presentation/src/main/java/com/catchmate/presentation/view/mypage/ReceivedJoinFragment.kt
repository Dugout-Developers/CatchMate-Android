package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentReceivedJoinBinding
import com.catchmate.presentation.interaction.OnReceivedEnrollClickListener
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ReceivedJoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceivedJoinFragment :
    BaseFragment<FragmentReceivedJoinBinding>(FragmentReceivedJoinBinding::inflate),
    OnReceivedEnrollClickListener {
    private var newCount: Int = -1
    private val receivedJoinViewModel: ReceivedJoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newCount = arguments?.getInt("newCount") ?: -1
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initRecyclerView()
        initViewModel()
    }

    private fun initHeader() {
        binding.layoutHeaderReceivedJoin.apply {
            tvHeaderTextTitle.setText(R.string.mypage_received_join)
            if (newCount > 0) {
                tvHeaderTextUnreadMessageCountBadge.visibility = View.VISIBLE
                tvHeaderTextUnreadMessageCountBadge.text = newCount.toString()
            } else {
                tvHeaderTextUnreadMessageCountBadge.visibility = View.INVISIBLE
            }
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvReceivedJoinList.apply {
            adapter = ReceivedJoinAdapter(requireContext(), layoutInflater, this@ReceivedJoinFragment)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initViewModel() {
        receivedJoinViewModel.getAllReceivedEnroll()
        receivedJoinViewModel.getAllReceivedEnrollResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val groupedData = response.enrollInfoList.groupBy { it.boardInfo.boardId }
                val adapter = binding.rvReceivedJoinList.adapter as ReceivedJoinAdapter
                adapter.updateEnrollInfoList(groupedData)
            }
        }
    }

    override fun onReceivedEnrollClick(boardId: Long) {
        val dialog = ReceivedEnrollScrollDialogFragment.newInstance(boardId)
        dialog.show(parentFragmentManager, "ReceivedEnrollDialog")
    }
}
