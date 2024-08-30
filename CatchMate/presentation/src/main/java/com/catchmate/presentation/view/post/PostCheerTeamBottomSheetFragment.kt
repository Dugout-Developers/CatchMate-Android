package com.catchmate.presentation.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentPostCheerTeamBottomSheetBinding
import com.catchmate.presentation.interaction.OnCheerTeamSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostCheerTeamBottomSheetFragment(
    val homeTeam: String,
    val awayTeam: String,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentPostCheerTeamBottomSheetBinding? = null
    val binding get() = _binding!!

    private var selectedButton: TeamToggleCheckButtonView? = null
    private var cheerTeamSelectedListener: OnCheerTeamSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPostCheerTeamBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTeamToggleCheckButtonResources(homeTeam, binding.ttcbvPostCheerTeamHome)
        initTeamToggleCheckButtonResources(awayTeam, binding.ttcbvPostCheerTeamAway)
        initTeamButton()
        initFooter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTeamButton() {
        val teamButtons: List<TeamToggleCheckButtonView> =
            listOf(
                binding.ttcbvPostCheerTeamHome,
                binding.ttcbvPostCheerTeamAway
            )

        teamButtons.forEach { btn ->
            btn.binding.toggleTeamToggleCheckButton.setOnCheckedChangeListener { buttonView, isChecked ->
                btn.binding.cbTeamToggleCheckButton.isChecked = isChecked
                if (isChecked) {
                    selectedButton?.binding?.toggleTeamToggleCheckButton?.isChecked = false
                    buttonView.isChecked = true
                    selectedButton = btn
                    binding.layoutFooterPostCheerTeam.btnFooterOne.isEnabled = true
                } else {
                    binding.layoutFooterPostCheerTeam.btnFooterOne.isEnabled = false
                }
            }

            btn.binding.cbTeamToggleCheckButton.setOnCheckedChangeListener { buttonView, isChecked ->
                btn.binding.toggleTeamToggleCheckButton.isChecked = isChecked
                if (isChecked) {
                    selectedButton?.binding?.cbTeamToggleCheckButton?.isChecked = false
                    buttonView.isChecked = true
                    selectedButton = btn
                    binding.layoutFooterPostCheerTeam.btnFooterOne.isEnabled = true
                } else {
                    binding.layoutFooterPostCheerTeam.btnFooterOne.isEnabled = false
                }
            }

            btn.setOnClickListener {
                btn.binding.toggleTeamToggleCheckButton.isChecked = !btn.binding.toggleTeamToggleCheckButton.isChecked
            }
        }
    }

    private fun initFooter() {
        binding.layoutFooterPostCheerTeam.btnFooterOne.setOnClickListener {
            cheerTeamSelectedListener?.onCheerTeamSelected(selectedButton?.binding?.tvTeamToggleCheckButton?.text.toString())
            dismiss()
        }
    }

    fun setOnCheerTeamSelectedListener(listener: OnCheerTeamSelectedListener) {
        cheerTeamSelectedListener = listener
    }

    private fun initTeamToggleCheckButtonResources(teamName: String, buttonView: TeamToggleCheckButtonView) {
        buttonView.binding.tvTeamToggleCheckButton.text = teamName
        when (teamName) {
            "다이노스" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_nc_dinos_bg)
                }
            }
            "라이온즈" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_samsung_lions_bg)
                }
            }
            "랜더스" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_ssg_landers_bg)
                }
            }
            "베어스" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_doosan_bears_bg)
                }
            }
            "위즈" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_kt_wiz_bg)
                }
            }
            "이글스" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_hanwha_eagles_bg)
                }
            }
            "자이언츠" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_lotte_giants_bg)
                }
            }
            "타이거즈" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_kia_tigers_bg)
                }
            }
            "트윈스" -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_lg_twins_bg)
                }
            }
            else -> {
                buttonView.binding.apply {
                    toggleTeamToggleCheckButton.setBackgroundResource(R.drawable.selector_kiwoom_heroes_bg)
                }
            }
        }
    }
}
