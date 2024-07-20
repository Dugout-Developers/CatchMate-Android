package com.catchmate.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentHomeTeamFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeTeamFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentHomeTeamFilterBottomSheetBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeTeamFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initFooterButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFooterButton() {
        binding.layoutTeamFilterFooter.btnFooterOne.setText(R.string.application)
    }
}
