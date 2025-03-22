package com.catchmate.presentation.view.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentLoginBinding
import com.catchmate.presentation.databinding.LayoutAlertDialogBinding
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.catchmate.presentation.viewmodel.LoginViewModel
import com.catchmate.presentation.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
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
        initViewModel()
        initView()
        if (navigateCode == NAVIGATE_CODE_REISSUE) showAlertDialog()
    }

    private fun initViewModel() {
        loginViewModel.postLoginRequest.observe(viewLifecycleOwner) { request ->
            if (request != null) {
                Log.d(
                    "LoginFragment",
                    "LoginRequest\n${request.email}\n${request.provider}\n" +
                        "${request.providerId}\n${request.picture}\n${request.fcmToken}",
                )
                loginViewModel.postAuthLogin(request)
            } else {
                Log.e("로그인 취소", "로그인 취소")
            }
        }
        loginViewModel.postLoginResponse.observe(viewLifecycleOwner) { loginResponse ->
            if (loginResponse != null) {
                Log.d(
                    "LoginFragment",
                    "LoginResponse\nacc:${loginResponse.accessToken}\n" +
                        "ref:${loginResponse.refreshToken}\n bool:${loginResponse.isFirstLogin}",
                )

                when (loginResponse.isFirstLogin) {
                    true -> {
                        val postLoginRequest = loginViewModel.postLoginRequest.value!!
                        val userInfo =
                            PostUserAdditionalInfoRequest(
                                postLoginRequest.email,
                                postLoginRequest.providerId,
                                postLoginRequest.provider,
                                postLoginRequest.picture,
                                postLoginRequest.fcmToken,
                                "",
                                "",
                                "",
                                -1,
                                "",
                            )
                        val bundle = Bundle()
                        bundle.putSerializable("userInfo", userInfo)
                        findNavController().navigate(R.id.action_loginFragment_to_termsAndConditionFragment, bundle)
                        loginViewModel.initPostLoginRequest()
                        loginViewModel.initPostLoginResponse()
                    }
                    false -> {
                        localDataViewModel.saveAccessToken(loginResponse.accessToken!!)
                        localDataViewModel.saveRefreshToken(loginResponse.refreshToken!!)
                        localDataViewModel.saveProvider(loginViewModel.postLoginRequest.value?.provider!!)
                        mainViewModel.setGuestLogin(false)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
        loginViewModel.noCredentialException.observe(viewLifecycleOwner) { exception ->
            Snackbar.make(requireView(), exception, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        binding.apply {
            cvLoginKakao.setOnClickListener {
                loginViewModel.kakaoLogin()
            }
            ivLoginNaver.setOnClickListener {
                loginViewModel.naverLogin(requireActivity())
            }
            ivLoginGoogle.setOnClickListener {
                loginViewModel.googleLogin(requireActivity())
            }
            tvLoginGuest.setOnClickListener {
                mainViewModel.setGuestLogin(true)
                localDataViewModel.saveAccessToken("")
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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
}
