package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentAccountInfoBinding
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.AccountInfoViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountInfoFragment : BaseFragment<FragmentAccountInfoBinding>(FragmentAccountInfoBinding::inflate) {
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private val accountInfoViewModel: AccountInfoViewModel by viewModels()
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = arguments?.getString("email") ?: ""
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initViewModel()
    }

    private fun initHeader() {
        binding.layoutHeaderAccountInfo.apply {
            tvHeaderTextTitle.setText(R.string.mypage_setting_user_info)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initLoginInfo(provider: String) {
        binding.apply {
            tvAccountInfoEmail.text = email
            val targetResource =
                when (provider) {
                    "kakao" -> R.drawable.vec_login_kakao
                    "naver" -> R.drawable.vec_login_naver
                    else -> R.drawable.vec_login_google
                }
            Glide
                .with(this@AccountInfoFragment)
                .load(targetResource)
                .into(ivAccountInfoLoginPlatform)
        }
    }

    private fun initViewModel() {
        localDataViewModel.getProvider()
        localDataViewModel.getRefreshToken()
        localDataViewModel.provider.observe(viewLifecycleOwner) { provider ->
            initLoginInfo(provider)
        }
        localDataViewModel.refreshToken.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                initLogoutBtn()
            }
        }
    }

    private fun initLogoutBtn() {
        binding.btnAccountInfoLogout.setOnClickListener {
            accountInfoViewModel.logout(localDataViewModel.refreshToken.value!!)
            accountInfoViewModel.logoutResponse.observe(viewLifecycleOwner) { response ->
                Log.e("LOGOUT", response.state.toString())
                // 로그아웃 시 로컬 데이터 삭제 및 화면 이동
                localDataViewModel.logout()
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.accountInfoFragment, true)
                        .build()
                findNavController().navigate(R.id.action_accountInfoFragment_to_loginFragment, null, navOptions)
            }
        }
    }
}
