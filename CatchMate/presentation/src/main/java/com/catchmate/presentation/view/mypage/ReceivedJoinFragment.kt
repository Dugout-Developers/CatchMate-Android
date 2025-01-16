package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentReceivedJoinBinding
import com.catchmate.presentation.interaction.OnReceivedEnrollClickListener
import com.catchmate.presentation.viewmodel.ReceivedJoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceivedJoinFragment :
    Fragment(),
    OnReceivedEnrollClickListener {
    private var _binding: FragmentReceivedJoinBinding? = null
    val binding get() = _binding!!

    private var newCount: Int = -1
    private val receivedJoinViewModel: ReceivedJoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newCount = arguments?.getInt("newCount") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReceivedJoinBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
