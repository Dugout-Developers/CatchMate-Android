package com.catchmate.presentation.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentPostHeadCountBottomSheetBinding
import com.catchmate.presentation.interaction.OnPeopleCountSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostHeadCountBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPostHeadCountBottomSheetBinding? = null
    val binding get() = _binding!!

    private var peopleCountSelectedListener: OnPeopleCountSelectedListener? = null

    private val headCountArray =
        arrayOf(
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
        _binding = FragmentPostHeadCountBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initFooterButton()
        initNumberPicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setOnPeopleCountSelectedListener(listener: OnPeopleCountSelectedListener) {
        peopleCountSelectedListener = listener
    }

    private fun initFooterButton() {
        binding.layoutPostHeadCountFooter.btnFooterOne.run {
            isEnabled = true
            setText(R.string.complete)
            setOnClickListener {
                peopleCountSelectedListener?.onPeopleCountSelected(binding.npPostHeadCount.value + 2)
                dismiss()
            }
        }
    }

    private fun initNumberPicker() {
        binding.npPostHeadCount.run {
            wrapSelectorWheel = false
            minValue = 0
            maxValue = headCountArray.size - 1
            displayedValues = headCountArray
            value = 0
        }
    }
}
