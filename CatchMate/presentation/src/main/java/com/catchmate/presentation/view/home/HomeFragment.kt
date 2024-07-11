package com.catchmate.presentation.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        initAllFilter()
        initDateFilter()
        initTeamFilter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAllFilter() {
        binding.chipHomeAllFilter.run {
            ivFilterIcon.setImageResource(R.drawable.vec_all_hamburger_20dp)
            tvFilterName.setText(R.string.home_filter_all)
        }
    }

    private fun initDateFilter() {
        binding.chipHomeDateFilter.run {
            ivFilterIcon.setImageResource(R.drawable.vec_all_down_arrow_20dp)
            tvFilterName.setText(R.string.home_filter_date)
        }
    }

    private fun initTeamFilter() {
        binding.chipHomeTeamFilter.run {
            ivFilterIcon.setImageResource(R.drawable.vec_all_down_arrow_20dp)
            tvFilterName.setText(R.string.filter_title_team)
        }
    }
}
