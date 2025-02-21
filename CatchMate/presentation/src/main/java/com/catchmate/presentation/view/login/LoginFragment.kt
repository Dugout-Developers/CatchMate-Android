package com.catchmate.presentation.view.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.user.PostUserAdditionalInfoRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentLoginBinding
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.catchmate.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initKakaoLoginBtn()
        initNaverLoginBtn()
        initGoogleLoginBtn()
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
                        findNavController().navigate(R.id.signupFragment, bundle)
                    }
                    false -> {
                        localDataViewModel.saveAccessToken(loginResponse.accessToken!!)
                        localDataViewModel.saveRefreshToken(loginResponse.refreshToken!!)
                        localDataViewModel.saveProvider(loginViewModel.postLoginRequest.value?.provider!!)
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }

    private fun initKakaoLoginBtn() {
        binding.cvLoginKakao.setOnClickListener {
            loginViewModel.kakaoLogin()
        }
    }

    private fun initNaverLoginBtn() {
        binding.ivLoginNaver.setOnClickListener {
            loginViewModel.naverLogin()
        }
    }

    private fun initGoogleLoginBtn() {
        binding.ivLoginGoogle.setOnClickListener {
            loginViewModel.googleLogin()
        }
    }
}
