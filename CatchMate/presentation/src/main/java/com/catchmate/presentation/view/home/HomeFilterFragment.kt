package com.catchmate.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentHomeFilterBinding

class HomeFilterFragment : Fragment() {
    private var _binding: FragmentHomeFilterBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initFooterButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFooterButton() {
        binding.layoutFooterHomeFilter.btnFooterOne.run {
            isEnabled = true
            setText(R.string.application)
        }
    }

    private fun initHeader() {
        binding.layoutHeaderHomeFilter.run {
            tvHeaderTextTitle.setText(R.string.home_filter_title)
            tvHeaderTextSub.visibility = View.GONE
        }
    }
}
