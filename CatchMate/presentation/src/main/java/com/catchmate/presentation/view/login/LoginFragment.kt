package com.catchmate.presentation.view.login

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.LayoutAlertDialogBinding
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.catchmate.presentation.viewmodel.MainViewModel
import com.catchmate.presentation.viewmodel.login.LoginEvent
import com.catchmate.presentation.viewmodel.login.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val navigateCode by lazy { arguments?.getInt("navigateCode") }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        enableDoubleBackPressedExit = true
        observeEvent()
        if (navigateCode == NAVIGATE_CODE_REISSUE) showAlertDialog()
    }

    private fun observeEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.event.collect { event ->
                    when (event) {
                        is LoginEvent.NavigateToSignUp -> {
                            val bundle =
                                Bundle()
                                    .apply {
                                        putSerializable("userInfo", event.userInfo)
                                    }
                            findNavController().navigate(R.id.action_loginFragment_to_termsAndConditionFragment, bundle)
                        }

                        is LoginEvent.NavigateToHome -> {
                            mainViewModel.setGuestLogin(false)
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }

                        is LoginEvent.ShowSnackBar -> {
                            Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showAlertDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutAlertDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.apply {
            tvAlertDialogTitle.setText(R.string.login_information_expired)
            tvAlertDialogPositive.apply {
                setText(R.string.complete)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    @Composable
    override fun ComposeContent() {
        val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

        LoginScreen(
            isLoading = uiState.isLoading, // indicator 표시를 위한 boolean 속성
            onKakaoLoginClick = { loginViewModel.kakaoLogin(requireActivity()) },
            onNaverLoginClick = { loginViewModel.naverLogin(requireActivity()) },
            onGoogleLoginClick = { loginViewModel.googleLogin(requireActivity()) },
            onGuestLoginClick = {
                mainViewModel.setGuestLogin(true)
                localDataViewModel.saveAccessToken("")
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            },
        )
    }
}
