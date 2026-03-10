package com.catchmate.presentation.view.onboarding

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.view.base.BaseComposeFragment

class SignupCompleteFragment : BaseComposeFragment() {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        enableDoubleBackPressedExit = true
    }

    @Composable
    override fun ComposeContent() {
        SignUpCompleteScreen(
            { findNavController().navigate(R.id.action_signupCompleteFragment_to_homeFragment) },
        )
    }
}
