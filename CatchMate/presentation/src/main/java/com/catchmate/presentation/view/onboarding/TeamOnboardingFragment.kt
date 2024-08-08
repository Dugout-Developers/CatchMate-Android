package com.catchmate.presentation.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentTeamOnboardingBinding

class TeamOnboardingFragment : Fragment() {
    private var _binding: FragmentTeamOnboardingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTeamOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initFooterBtn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutTeamOnboardingHeader.apply {
            imgbtnOnboardingIndicator2.setImageResource(R.drawable.vec_onboarding_indicator_activated_6dp)
            imgbtnOnboardingIndicator1.setOnClickListener {
                findNavController().popBackStack()
            }
            imgbtnOnboardingBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initFooterBtn() {
        binding.layoutTeamOnboardingFooter.btnFooterOne.apply {
            setText(R.string.next)
            isEnabled = true
            setOnClickListener {
                findNavController().navigate(R.id.action_teamOnboardingFragment_to_cheerStyleOnboardingFragment)
            }
        }
    }
}
