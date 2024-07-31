package com.catchmate.presentation.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentLoginBinding
import com.catchmate.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        loginViewModel.loginRequest.observe(viewLifecycleOwner) { loginRequest ->
            if (loginRequest != null) {
                Log.d(
                    "LoginFragment",
                    "LoginRequest\n${loginRequest.email}\n${loginRequest.provider}\n" +
                            "${loginRequest.providerId}\n${loginRequest.picture}\n${loginRequest.fcmToken}"
                )
                loginViewModel.postLogin(loginRequest)
            }
        }
        loginViewModel.loginResponse.observe(viewLifecycleOwner) { loginResponse ->
            if (loginResponse != null) {
                Log.d(
                    "LoginFragment",
                    "LoginResponse\nacc:${loginResponse.accessToken}\n" +
                        "ref:${loginResponse.refreshToken}\n bool:${loginResponse.isFirstLogin}"
                )
                when (loginResponse.isFirstLogin) {
                    true -> findNavController().navigate(R.id.signupFragment)
                    false -> findNavController().navigate(R.id.homeFragment)
                }
            }
        }
    }

    private fun initKakaoLoginBtn() {
        binding.btnLoginKakao.setOnClickListener {
            loginViewModel.kakaoLogin()
        }
    }

    private fun initNaverLoginBtn() {
        binding.imgbtnLoginNaver.setOnClickListener {
            loginViewModel.naverLogin()
        }
    }

    private fun initGoogleLoginBtn() {
        binding.imgbtnLoginGoogle.setOnClickListener {
            loginViewModel.googleLogin()
        }
    }
}
