package com.catchmate.presentation.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentCheerStyleOnboardingBinding

class CheerStyleOnboardingFragment : Fragment() {
    private var _binding: FragmentCheerStyleOnboardingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCheerStyleOnboardingBinding.inflate(inflater, container, false)
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
        binding.layoutCheerStyleOnboardingNext.btnFooterOne.apply {
            setText(R.string.next)
            isEnabled = true
            setOnClickListener {
                findNavController().navigate(R.id.action_cheerStyleOnboardingFragment_to_signupCompleteFragment)
            }
        }
    }

    private fun initHeader() {
        binding.layoutCheerStyleOnboardingHeader.apply {
            imgbtnOnboardingIndicator3.setImageResource(R.drawable.vec_onboarding_indicator_activated_6dp)
            imgbtnOnboardingBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }
}
