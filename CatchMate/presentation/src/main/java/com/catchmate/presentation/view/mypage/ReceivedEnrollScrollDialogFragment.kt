package com.catchmate.presentation.view.mypage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.catchmate.domain.model.TestEnrollInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentReceivedEnrollScrollDialogBinding

class ReceivedEnrollScrollDialogFragment : DialogFragment() {
    private var _binding: FragmentReceivedEnrollScrollDialogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentReceivedEnrollScrollDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvReceivedEnrollScrollDialog.apply {
            adapter = ReceivedEnrollAdapter(requireContext(), layoutInflater)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvReceivedEnrollScrollDialog)
    }

    private fun setData() {
        val enrollInfo =
            listOf(
                TestEnrollInfo(
                    "지은",
                    "이글스",
                    "여성",
                    "20대",
                    "1월 14일 01:00",
                    "직관신청합니다."
                ),
                TestEnrollInfo(
                    "창근",
                    "다이노스",
                    "남성",
                    "30대",
                    "10월 5일 15:28",
                    "같이가요"
                ),
                TestEnrollInfo(
                    "유빈",
                    "이글스",
                    "여성",
                    "20대",
                    "1월 19일 03:00",
                    "직관신청합니다."
                ),
            )
        val adapter = binding.rvReceivedEnrollScrollDialog.adapter as ReceivedEnrollAdapter
        adapter.updateEnrollList(enrollInfo)
    }
}
