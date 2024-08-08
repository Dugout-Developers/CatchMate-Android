package com.catchmate.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catchmate.presentation.databinding.FragmentHomeHeadCountFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeHeadCountFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentHomeHeadCountFilterBottomSheetBinding? = null
    val binding get() = _binding!!

    private val headCountArray =
        arrayOf(
            "1명",
            "2명",
            "3명",
            "4명",
            "5명",
            "6명",
            "7명",
            "8명",
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeHeadCountFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNumberPicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initNumberPicker() {
        binding.npHomeHeadCountFilter.run {
            wrapSelectorWheel = false
            minValue = 0
            maxValue = headCountArray.size - 1
            displayedValues = headCountArray
            value = 0
        }
    }
}
