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
import com.catchmate.presentation.databinding.FragmentSentJoinBinding
import com.catchmate.presentation.interaction.OnPostItemClickListener
import com.catchmate.presentation.viewmodel.SentJoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SentJoinFragment :
    Fragment(),
    OnPostItemClickListener {
    private var _binding: FragmentSentJoinBinding? = null
    val binding get() = _binding!!

    private val sentJoinViewModel: SentJoinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSentJoinBinding.inflate(inflater, container, false)
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
        }
    }

    private fun initViewModel() {
        sentJoinViewModel.getRequestedEnrollList()
        sentJoinViewModel.requestedEnrollList.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val adapter = binding.rvSentJoinPostList.adapter as SentJoinAdapter
                adapter.updateList(response.enrollInfoList)
            }
        }
    }

    override fun onPostItemClicked(boardId: Long) {
        val bundle = Bundle()
        bundle.putLong("boardId", boardId)
        findNavController().navigate(R.id.action_sentJoinFragment_to_readPostFragment, bundle)
    }
}
