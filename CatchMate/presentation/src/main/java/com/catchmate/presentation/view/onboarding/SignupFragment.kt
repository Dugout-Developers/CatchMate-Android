package com.catchmate.presentation.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
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

    private fun initFooterBtn() {
        binding.layoutSignupFooter.btnFooterOne.apply {
            setText(R.string.next)
            isEnabled = true // 임시
            setOnClickListener {
                findNavController().navigate(R.id.action_signupFragment_to_teamOnboardingFragment)
            }
        }
    }

    private fun initHeader() {
        binding.layoutSignupHeader.apply {
            imgbtnOnboardingIndicator1.apply {
                setImageResource(R.drawable.vec_onboarding_indicator_activated_6dp)
            }
            imgbtnOnboardingBack.setOnClickListener {
                findNavController().popBackStack()
                // 로컬에 저장된 로그인 정보 삭제
            }
        }
    }
}
