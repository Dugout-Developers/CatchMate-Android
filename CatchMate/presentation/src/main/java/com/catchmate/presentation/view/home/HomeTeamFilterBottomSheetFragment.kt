package com.catchmate.presentation.view.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catchmate.presentation.databinding.FragmentHomeTeamFilterBottomSheetBinding
import com.catchmate.presentation.interaction.OnClubFilterSelectedListener
import com.catchmate.presentation.util.ClubUtils
import com.catchmate.presentation.view.post.TeamToggleCheckButtonView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeTeamFilterBottomSheetFragment(
    private val defaultClub: Int?,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentHomeTeamFilterBottomSheetBinding? = null
    val binding get() = _binding!!

    private var clubSelectedListener: OnClubFilterSelectedListener? = null
    private var selectedButton: TeamToggleCheckButtonView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val behavior = dialog.behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        return dialog
    }

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
        initTeamButtons()
        initFooter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTeamButtons() {
        val teamToggleCheckButtons: List<TeamToggleCheckButtonView> =
            listOf(
                binding.ttcbvTeamFilterKt,
                binding.ttcbvTeamFilterLg,
                binding.ttcbvTeamFilterNc,
                binding.ttcbvTeamFilterLotte,
                binding.ttcbvTeamFilterKia,
                binding.ttcbvTeamFilterSsg,
                binding.ttcbvTeamFilterDoosan,
                binding.ttcbvTeamFilterHanwha,
                binding.ttcbvTeamFilterKiwoom,
                binding.ttcbvTeamFilterSamsung,
            )

        teamToggleCheckButtons.forEach { btn ->
            btn.binding.toggleTeamToggleCheckButton.setOnCheckedChangeListener { buttonView, isChecked ->
                btn.binding.cbTeamToggleCheckButton.isChecked = isChecked
                if (isChecked) {
                    selectedButton?.binding?.toggleTeamToggleCheckButton?.isChecked = false
                    buttonView.isChecked = true
                    selectedButton = btn
                }
            }

            btn.binding.cbTeamToggleCheckButton.setOnCheckedChangeListener { buttonView, isChecked ->
                btn.binding.toggleTeamToggleCheckButton.isChecked = isChecked
                if (isChecked) {
                    selectedButton?.binding?.cbTeamToggleCheckButton?.isChecked = false
                    buttonView.isChecked = true
                    selectedButton = btn
                }
            }

            if (defaultClub != null && btn.binding.tvTeamToggleCheckButton.text == ClubUtils.convertClubIdToName(defaultClub)) {
                btn.binding.toggleTeamToggleCheckButton.isChecked = true
            }

            btn.setOnClickListener {
                btn.binding.toggleTeamToggleCheckButton.isChecked = !btn.binding.toggleTeamToggleCheckButton.isChecked
            }
        }
    }

    private fun initFooter() {
        binding.layoutTeamFilterFooter.apply {
            btnFilterFooterReset.setOnClickListener {
                if (selectedButton != null) {
                    selectedButton?.binding?.toggleTeamToggleCheckButton?.isChecked = false
                    selectedButton = null
                }
            }
            btnFilterFooterApply.setOnClickListener {
                if (selectedButton == null) {
                    clubSelectedListener?.onClubFilterSelected(null)
                } else {
                    clubSelectedListener?.onClubFilterSelected(
                        ClubUtils.convertClubNameToId(
                            selectedButton
                                ?.binding
                                ?.tvTeamToggleCheckButton
                                ?.text
                                .toString(),
                        ),
                    )
                }
                dismiss()
            }
        }
    }

    fun setOnClubSelectedListener(listener: OnClubFilterSelectedListener) {
        clubSelectedListener = listener
    }
}
