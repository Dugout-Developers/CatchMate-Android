package com.catchmate.presentation.view.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initHeader()
        initDateFilter()
        initTeamFilter()
        initHeadCountFilter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutHeaderHome.apply {
            imgbtnHeaderHomeNotification.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
            }
        }
    }

    private fun initDateFilter() {
        binding.hfvHomeDateFilter.setOnClickListener {
            val dateFilterBottomSheet = HomeDateFilterBottomSheetFragment()
            dateFilterBottomSheet.show(requireActivity().supportFragmentManager, dateFilterBottomSheet.tag)
        }
    }

    private fun initTeamFilter() {
        binding.hfvHomeTeamFilter.setOnClickListener {
            val teamFilterBottomSheet = HomeTeamFilterBottomSheetFragment()
            teamFilterBottomSheet.show(requireActivity().supportFragmentManager, teamFilterBottomSheet.tag)
        }
    }

    private fun initHeadCountFilter() {
        binding.hfvHomeMemberCountFilter.setOnClickListener {
            val headCountFilterBottomSheet = HomeHeadCountFilterBottomSheetFragment()
            headCountFilterBottomSheet.show(requireActivity().supportFragmentManager, headCountFilterBottomSheet.tag)
        }
    }
}
