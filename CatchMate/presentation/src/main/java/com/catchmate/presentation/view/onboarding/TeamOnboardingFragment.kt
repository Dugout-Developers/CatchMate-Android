package com.catchmate.presentation.view.onboarding

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.enumclass.Club
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.viewmodel.onboarding.TeamOnboardingSideEffect
import com.catchmate.presentation.viewmodel.onboarding.TeamOnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamOnboardingFragment : BaseComposeFragment() {
    private val teamOnboardingViewModel: TeamOnboardingViewModel by viewModels()
    private lateinit var userInfo: PostUserAdditionalInfoRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = getUserInfo()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeEvent()
        setUiState()
    }

    private fun setUiState() {
        val logoList =
            listOf<Int>(
                R.drawable.vec_all_kia_tigers_logo,
                R.drawable.vec_all_samsung_lions_logo,
                R.drawable.vec_all_lg_twins_logo,
                R.drawable.vec_all_doosan_bears_logo,
                R.drawable.vec_all_kt_wiz_logo,
                R.drawable.vec_all_ssg_landers_logo,
                R.drawable.vec_all_lotte_giants_logo,
                R.drawable.vec_all_hanwha_eagles_logo,
                R.drawable.vec_all_nc_dinos_logo,
                R.drawable.vec_all_kiwoom_heroes_logo,
                R.drawable.imb_baseball_beginner_icon,
                R.drawable.img_pacifist_icon,
            )
        val textList =
            listOf<String>(
                Club.KIA.teamName,
                Club.SAMSUNG.teamName,
                Club.LG.teamName,
                Club.DOOSAN.teamName,
                Club.KT.teamName,
                Club.SSG.teamName,
                Club.LOTTE.teamName,
                Club.HANWHA.teamName,
                Club.NC.teamName,
                Club.KIWOOM.teamName,
                Club.BEGINNER.teamName,
                Club.PACIFIST.teamName,
            )
        teamOnboardingViewModel.setTeamScreenData(userInfo.nickName, logoList, textList)
    }

    private fun observeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                teamOnboardingViewModel.sideEffect.collect { effect ->
                    when (effect) {
                        TeamOnboardingSideEffect.NavigateToBack -> {
                            findNavController().popBackStack()
                        }
                        is TeamOnboardingSideEffect.NavigateToNext -> {
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
                                    effect.clubId,
                                    "",
                                )
                            val bundle = Bundle()
                            bundle.putSerializable("userInfo", newUserInfo)
                            findNavController().navigate(R.id.action_teamOnboardingFragment_to_cheerStyleOnboardingFragment, bundle)
                        }
                    }
                }
            }
        }
    }

    private fun getUserInfo(): PostUserAdditionalInfoRequest =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("userInfo", PostUserAdditionalInfoRequest::class.java)!!
        } else {
            arguments?.getSerializable("userInfo") as PostUserAdditionalInfoRequest
        }

    @Composable
    override fun ComposeContent() {
        val uiState by teamOnboardingViewModel.uiState.collectAsState()
        TeamOnboardingScreen(
            uiState = uiState,
            onEvent = teamOnboardingViewModel::onEvent,
        )
    }
}
