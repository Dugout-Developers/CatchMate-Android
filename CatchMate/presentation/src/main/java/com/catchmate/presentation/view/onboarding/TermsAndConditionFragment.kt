package com.catchmate.presentation.view.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.viewmodel.onboarding.TermsAndConditionSideEffect
import com.catchmate.presentation.viewmodel.onboarding.TermsAndConditionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TermsAndConditionFragment : BaseComposeFragment() {
    private val termsAndConditionViewModel: TermsAndConditionViewModel by viewModels()
    private lateinit var userInfo: PostUserAdditionalInfoRequest

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        userInfo = getUserInfo()
        observeEvent()
    }

    private fun getUserInfo(): PostUserAdditionalInfoRequest =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("userInfo", PostUserAdditionalInfoRequest::class.java)!!
        } else {
            arguments?.getSerializable("userInfo") as PostUserAdditionalInfoRequest
        }

    private fun observeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                termsAndConditionViewModel.sideEffect.collect { effect ->
                    when (effect) {
                        TermsAndConditionSideEffect.NavigateBack -> {
                            findNavController().popBackStack()
                        }

                        is TermsAndConditionSideEffect.NavigateToNext -> {
                            val bundle = Bundle()
                            bundle.putSerializable("userInfo", userInfo)
                            bundle.putBoolean("isMarketingPushChecked", effect.isMarketingPushChecked)
                            findNavController().navigate(R.id.action_termsAndConditionFragment_to_signupFragment, bundle)
                        }

                        is TermsAndConditionSideEffect.NavigateToWeb -> {
                            val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                            startActivity(intent)
                        }

                        is TermsAndConditionSideEffect.ShowError -> {
                            Snackbar.make(requireView(), effect.message, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        val uiState by termsAndConditionViewModel.uiState.collectAsState()
        TermsAndConditionScreen(
            uiState = uiState,
            onEvent = termsAndConditionViewModel::onEvent,
        )
    }
}
