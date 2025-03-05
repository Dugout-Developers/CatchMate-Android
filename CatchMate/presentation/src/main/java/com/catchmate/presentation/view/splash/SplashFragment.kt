package com.catchmate.presentation.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentSplashBinding
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.view.home.HomeFragment
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private var isDelayFinished = false

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        localDataViewModel.accessToken.observe(viewLifecycleOwner) { accessToken ->
            if (isDelayFinished) navigateToNextScreen(accessToken)
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                isDelayFinished = true
                localDataViewModel.getAccessToken()
            },
            1000,
        )
    }

    private fun navigateToNextScreen(accessToken: String?) {
        if (accessToken.isNullOrEmpty()) {
            Log.e("splash", "accesstoken null or empty")
            findNavController().navigate(R.id.loginFragment)
        } else {
            Log.e("splash", "accesstoken not null or empty")
            findNavController().navigate(R.id.homeFragment)
        }
    }
}
