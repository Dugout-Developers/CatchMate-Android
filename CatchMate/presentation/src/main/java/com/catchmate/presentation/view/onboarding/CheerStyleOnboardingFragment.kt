package com.catchmate.presentation.view.onboarding

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.viewmodel.MainViewModel
import com.catchmate.presentation.viewmodel.onboarding.CheerStyleOnboardingSideEffect
import com.catchmate.presentation.viewmodel.onboarding.CheerStyleOnboardingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheerStyleOnboardingFragment : BaseComposeFragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val cheerStyleOnboardingViewModel: CheerStyleOnboardingViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUiState()
        observeEvent()
    }

    private fun observeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cheerStyleOnboardingViewModel.sideEffect.collect { effect ->
                    when (effect) {
                        CheerStyleOnboardingSideEffect.NavigateToBack -> {
                            findNavController().popBackStack()
                        }
                        CheerStyleOnboardingSideEffect.NavigateToNext -> {
                            mainViewModel.setGuestLogin(false)
                            findNavController().navigate(R.id.action_cheerStyleOnboardingFragment_to_signupCompleteFragment)
                        }
                        is CheerStyleOnboardingSideEffect.ShowSnackBar -> {
                            Snackbar.make(requireView(), effect.message, Snackbar.LENGTH_SHORT)
                        }
                    }
                }
            }
        }
    }

    private fun setUiState() {
        val imgList =
            listOf(
                R.drawable.img_director_icon,
                R.drawable.img_mother_bird_icon,
                R.drawable.img_cheer_leader_icon,
                R.drawable.img_glutton_icon,
                R.drawable.img_stone_icon,
                R.drawable.img_bodhisattva_icon,
            )
        val textList =
            listOf(
                Pair(getString(R.string.cheer_style_director), getString(R.string.cheer_style_director_explain)),
                Pair(getString(R.string.cheer_style_mother_bird), getString(R.string.cheer_style_mother_bird_explain)),
                Pair(getString(R.string.cheer_style_cheer_leader), getString(R.string.cheer_style_cheer_leader_explain)),
                Pair(getString(R.string.cheer_style_glutton), getString(R.string.cheer_style_glutton_explain)),
                Pair(getString(R.string.cheer_style_stone), getString(R.string.cheer_style_stone_explain)),
                Pair(getString(R.string.cheer_style_bodhisattva), getString(R.string.cheer_style_bodhisattva_explain)),
            )
        cheerStyleOnboardingViewModel.setCheerStyleScreenData(imgList, textList)
    }

    @Composable
    override fun ComposeContent() {
        val uiState by cheerStyleOnboardingViewModel.uiState.collectAsState()
        CheerStyleOnboardingScreen(
            uiState = uiState,
            onEvent = cheerStyleOnboardingViewModel::onEvent,
        )
    }
}
