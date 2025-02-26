package com.catchmate.presentation.view.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ActivityMainBinding
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val localDataViewModel: LocalDataViewModel by viewModels()

    val permissionList =
        arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.POST_NOTIFICATIONS,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions(permissionList, 0)
        initViewModel()
        localDataViewModel.getAccessToken()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() {
        localDataViewModel.accessToken.observe(this) { accessToken ->
            if (accessToken.isNullOrEmpty()) {
                Log.e("메인a", "accesstoken null or empty")
                binding.fragmentcontainerviewMain.findNavController().navigate(R.id.loginFragment)
            } else {
                Log.e("메인a", "accesstoken not null or empty")
                binding.fragmentcontainerviewMain.findNavController().navigate(R.id.homeFragment)
            }
            initNavController()
            initBottomNavigationView()
        }
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentcontainerview_main) as NavHostFragment
        val navController = navHostFragment.navController

        binding.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment,
                    R.id.myPageFragment,
                    R.id.favoriteFragment,
                    R.id.chattingHomeFragment,
                    -> {
                        bottomnavigationviewMain.apply {
                            alpha = 0f
                            visibility = View.VISIBLE
                            animate().alpha(1f).setDuration(100).start()
                        }
                    }
                    else -> {
                        bottomnavigationviewMain.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initBottomNavigationView() {
        binding.bottomnavigationviewMain.apply {
            setOnItemSelectedListener {
                if (it.itemId == selectedItemId) {
                    return@setOnItemSelectedListener false
                }
                when (it.itemId) {
                    R.id.menuitem_home -> {
                        binding.fragmentcontainerviewMain.findNavController().navigate(R.id.homeFragment)
                    }
                    R.id.menuitem_favorite -> {
                        if (localDataViewModel.accessToken.value.isNullOrEmpty()) {
                            Snackbar.make(this, R.string.all_guest_snackbar, Snackbar.LENGTH_SHORT).show()
                        } else {
                            binding.fragmentcontainerviewMain.findNavController().navigate(R.id.favoriteFragment)
                        }
                    }
                    R.id.menuitem_post -> {
                        if (localDataViewModel.accessToken.value.isNullOrEmpty()) {
                            Snackbar.make(this, R.string.all_guest_snackbar, Snackbar.LENGTH_SHORT).show()
                        } else {
                            binding.fragmentcontainerviewMain.findNavController().navigate(R.id.addPostFragment)
                        }
                    }
                    R.id.menuitem_chatting -> {
                        if (localDataViewModel.accessToken.value.isNullOrEmpty()) {
                            Snackbar.make(this, R.string.all_guest_snackbar, Snackbar.LENGTH_SHORT).show()
                        } else {
                            binding.fragmentcontainerviewMain.findNavController().navigate(R.id.chattingHomeFragment)
                        }
                    }
                    else -> {
                        binding.fragmentcontainerviewMain.findNavController().navigate(R.id.myPageFragment)
                    }
                }
                true
            }
        }
    }
}
