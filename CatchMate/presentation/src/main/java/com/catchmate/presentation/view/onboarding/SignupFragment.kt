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
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.util.DateUtils
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.viewmodel.onboarding.SignUpSideEffect
import com.catchmate.presentation.viewmodel.onboarding.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseComposeFragment() {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var userInfo: PostUserAdditionalInfoRequest
    private val isMarketingPushChecked by lazy { arguments?.getBoolean("isMarketingPushChecked") ?: false }

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
                signUpViewModel.sideEffect.collect { effect ->
                    when (effect) {
                        SignUpSideEffect.NavigateToBack -> {
                            findNavController().popBackStack()
                        }

                        is SignUpSideEffect.NavigateToNext -> {
                            if (effect.nickname.isNotEmpty() && effect.gender.isNotEmpty() && effect.birthDate.isNotEmpty()) {
                                val newUserInfo =
                                    PostUserAdditionalInfoRequest(
                                        userInfo.email,
                                        userInfo.providerId,
                                        userInfo.provider,
                                        userInfo.profileImageUrl,
                                        userInfo.fcmToken,
                                        if (effect.gender == "남성") "M" else "F",
                                        effect.nickname,
                                        DateUtils.formatBirthDate(effect.birthDate),
                                        -1,
                                        "",
                                    )
                                val bundle = Bundle()
                                bundle.putSerializable("userInfo", newUserInfo)
                                bundle.putBoolean("isMarketingPushChecked", isMarketingPushChecked)
                                findNavController().navigate(R.id.action_signupFragment_to_teamOnboardingFragment, bundle)
                            }
                        }

                        is SignUpSideEffect.ShowSnackBar -> {
                            Snackbar.make(requireView(), effect.message, Snackbar.LENGTH_SHORT)
                        }
                    }
                }
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        val uiState by signUpViewModel.uiState.collectAsState()
        SignUpScreen(
            uiState = uiState,
            onEvent = signUpViewModel::onEvent,
        )
    }
}
