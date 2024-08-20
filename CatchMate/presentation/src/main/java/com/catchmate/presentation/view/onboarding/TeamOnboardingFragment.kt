package com.catchmate.presentation.view.onboarding

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.UserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentTeamOnboardingBinding

class TeamOnboardingFragment : Fragment() {
    private var _binding: FragmentTeamOnboardingBinding? = null
    val binding get() = _binding!!

    private lateinit var userInfo: UserAdditionalInfoRequest

    private var selectedButton: TeamButtonView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = getUserInfo()
    }

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
        initTeamButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTeamButtons() {
        val teamButtons: List<TeamButtonView> =
            listOf(
                binding.tbvNc,
                binding.tbvSamsung,
                binding.tbvSsg,
                binding.tbvDoosan,
                binding.tbvKt,
                binding.tbvHanwha,
                binding.tbvLotte,
                binding.tbvKia,
                binding.tbvLg,
                binding.tbvKiwoom,
                binding.tbvPacifist,
                binding.tbvBaseballBeginner,
            )

        teamButtons.forEach { btn ->
            btn.binding.toggleTeamButton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    selectedButton?.binding?.toggleTeamButton?.isChecked = false
                    buttonView.isChecked = true
                    selectedButton = btn
                    binding.layoutTeamOnboardingFooter.btnFooterOne.isEnabled = true
                } else {
                    binding.layoutTeamOnboardingFooter.btnFooterOne.isEnabled = false
                }
            }
        }
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
            setOnClickListener {
                val newUserInfo =
                    UserAdditionalInfoRequest(
                        userInfo.gender,
                        userInfo.nickName,
                        userInfo.birthDate,
                        selectedButton?.binding?.tvTeamButton?.text.toString(),
                        "",
                    )
                val bundle = Bundle()
                bundle.putSerializable("userInfo", newUserInfo)
                findNavController().navigate(R.id.action_teamOnboardingFragment_to_cheerStyleOnboardingFragment, bundle)
            }
        }
    }

    private fun getUserInfo(): UserAdditionalInfoRequest =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("userInfo", UserAdditionalInfoRequest::class.java)!!
        } else {
            arguments?.getSerializable("userInfo") as UserAdditionalInfoRequest
        }
}
