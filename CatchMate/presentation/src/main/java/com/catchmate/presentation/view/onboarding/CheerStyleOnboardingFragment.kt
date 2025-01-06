package com.catchmate.presentation.view.onboarding

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentCheerStyleOnboardingBinding
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.catchmate.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheerStyleOnboardingFragment : Fragment() {
    private var _binding: FragmentCheerStyleOnboardingBinding? = null
    val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()

    private lateinit var userInfo: PostUserAdditionalInfoRequest

    private var selectedButton: CheerStyleButtonView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = getUserInfo()
        Log.e("userInfo", "${userInfo.nickName},${userInfo.gender},${userInfo.birthDate},${userInfo.favoriteClubId}")
    }

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
        initTitle()
        initHeader()
        initFooterButton()
        initCheerStyleButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserInfo(): PostUserAdditionalInfoRequest =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("userInfo", PostUserAdditionalInfoRequest::class.java)!!
        } else {
            arguments?.getSerializable("userInfo") as PostUserAdditionalInfoRequest
        }

    private fun initTitle() {
        val title = getString(R.string.team_onboarding_title1)
        binding.tvCheerStyleOnboardingTitle1.text = title.format(userInfo.nickName)
    }

    private fun initFooterButton() {
        binding.layoutCheerStyleOnboardingNext.btnFooterOne.apply {
            setText(R.string.next)
            setOnClickListener {
                val newUserInfo =
                    PostUserAdditionalInfoRequest(
                        userInfo.email,
                        userInfo.providerId,
                        userInfo.provider,
                        userInfo.profileImageUrl,
                        userInfo.fcmToken,
                        userInfo.gender,
                        userInfo.nickName,
                        userInfo.birthDate,
                        userInfo.favoriteClubId,
                        selectedButton
                            ?.binding
                            ?.tvCheerStyleName
                            ?.text
                            .toString()
                            .replace(" 스타일", ""),
                    )
                postUserAdditionalInfo(newUserInfo)
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

    private fun initCheerStyleButtons() {
        val cheerStyleButtons: List<CheerStyleButtonView> =
            listOf(
                binding.csbvCheerStyleOnboardingDirector,
                binding.csbvCheerStyleOnboardingMotherBird,
                binding.csbvCheerStyleOnboardingCheerLeader,
                binding.csbvCheerStyleOnboardingGlutton,
                binding.csbvCheerStyleOnboardingStone,
                binding.csbvCheerStyleOnboardingBodhisattva,
            )

        cheerStyleButtons.forEach { btn ->
            btn.binding.toggleCheerStyle.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    selectedButton?.binding?.toggleCheerStyle?.isChecked = false
                    buttonView.isChecked = true
                    selectedButton = btn
                    binding.layoutCheerStyleOnboardingNext.btnFooterOne.isEnabled = true
                } else {
                    binding.layoutCheerStyleOnboardingNext.btnFooterOne.isEnabled = false
                }
            }
        }
    }

    private fun postUserAdditionalInfo(userAdditionalInfoRequest: PostUserAdditionalInfoRequest) {
        signUpViewModel.postUserAdditionalInfo(userAdditionalInfoRequest)
        signUpViewModel.userAdditionalInfoResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("response", "${response.userId}\n${response.accessToken}\n${response.refreshToken}")
                localDataViewModel.saveAccessToken(response.accessToken)
                localDataViewModel.saveRefreshToken(response.refreshToken)
                localDataViewModel.saveUserId(response.userId)
                findNavController().navigate(R.id.action_cheerStyleOnboardingFragment_to_signupCompleteFragment)
            }
        }
    }
}
