package com.catchmate.presentation.view.onboarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentTermsAndConditionBinding
import com.catchmate.presentation.view.base.BaseFragment

class TermsAndConditionFragment : BaseFragment<FragmentTermsAndConditionBinding>(FragmentTermsAndConditionBinding::inflate) {
    private var isAllChecked = false
    private var isFirstChecked = false
    private var isSecondChecked = false
    private var isThirdChecked = false

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            layoutHeaderTermsAndCondition.apply {
                imgbtnOnboardingBack.setOnClickListener {
                    findNavController().popBackStack()
                }
                imgbtnOnboardingIndicator1.setImageResource(R.drawable.vec_onboarding_indicator_activated_6dp)
            }
            layoutFooterTermsAndCondition.btnFooterOne.apply {
                setText(R.string.next)
                setOnClickListener {
                    findNavController().navigate(R.id.action_termsAndConditionFragment_to_signupFragment)
                }
            }

            layoutAllCheckTermsAndCondition.setOnClickListener {
                val isChecked = !cbAllCheckTermsAndCondition.isChecked
                cbAllCheckTermsAndCondition.isChecked = isChecked
                cbCheckFirstTermsAndCondition.isChecked = isChecked
                cbCheckSecondTermsAndCondition.isChecked = isChecked
                cbCheckThirdTermsAndCondition.isChecked = isChecked
                isAllChecked = isChecked
                isFirstChecked = isChecked
                isSecondChecked = isChecked
                isThirdChecked = isChecked
                updateButtonState()
            }

            layoutCheckFirstTermsAndCondition.setOnClickListener {
                cbCheckFirstTermsAndCondition.isChecked = !cbCheckFirstTermsAndCondition.isChecked
                isFirstChecked = cbCheckFirstTermsAndCondition.isChecked
                updateButtonState()
            }

            layoutCheckSecondTermsAndCondition.setOnClickListener {
                cbCheckSecondTermsAndCondition.isChecked = !cbCheckSecondTermsAndCondition.isChecked
                isSecondChecked = cbCheckSecondTermsAndCondition.isChecked
                updateButtonState()
            }

            layoutCheckThirdTermsAndCondition.setOnClickListener {
                cbCheckThirdTermsAndCondition.isChecked = !cbCheckThirdTermsAndCondition.isChecked
                isThirdChecked = cbCheckThirdTermsAndCondition.isChecked
                updateButtonState()
            }
        }
    }

    private fun updateButtonState() {
        if (isFirstChecked && isSecondChecked && isThirdChecked) { // 모든 약관 동의
            binding.cbAllCheckTermsAndCondition.isChecked = true
            binding.layoutFooterTermsAndCondition.btnFooterOne.isEnabled = true
        } else if (isFirstChecked && isSecondChecked) { // 필수 약관만 동의
            binding.layoutFooterTermsAndCondition.btnFooterOne.isEnabled = true
            binding.cbAllCheckTermsAndCondition.isChecked = false
        } else {
            binding.cbAllCheckTermsAndCondition.isChecked = false
            binding.layoutFooterTermsAndCondition.btnFooterOne.isEnabled = false
        }
    }
}
