package com.catchmate.presentation.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ActivityMainBinding
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.catchmate.presentation.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val localDataViewModel: LocalDataViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    val permissionList =
        arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.POST_NOTIFICATIONS,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions(permissionList, 0)

        Log.e("intent", "${intent.data} / ${intent.extras}")

        initViewModel()
        if (intent.extras == null) {
            localDataViewModel.getAccessToken()
        }
        initNavController()
        initBottomNavigationView()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            if (currentFocus is EditText) {
                val view = currentFocus as EditText
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                if (!rect.contains((ev.rawX.toInt()), ev.rawY.toInt())) {
                    view.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.extras?.let { bundle ->
            for (key in bundle.keySet()) {
                Log.e("intent_extras", "Key: $key, Value: ${bundle.get(key)}")
            }
        }
    }

    private fun initViewModel() {
        mainActivityViewModel.isGuestLogin.observe(this) { isGuest ->
            Log.e("메인a", "guest mode")
        }
        localDataViewModel.accessToken.observe(this) { accessToken ->
            if (accessToken.isNullOrEmpty()) {
                Log.e("splash", "accesstoken null or empty")
                binding.fragmentcontainerviewMain.findNavController().navigate(R.id.loginFragment)
            } else {
                Log.e("splash", "accesstoken not null or empty")
                binding.fragmentcontainerviewMain.findNavController().navigate(R.id.homeFragment)
            }
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
                val isGuest = mainActivityViewModel.isGuestLogin.value ?: false
                when (it.itemId) {
                    R.id.menuitem_home -> {
                        binding.fragmentcontainerviewMain.findNavController().navigate(R.id.homeFragment)
                    }
                    R.id.menuitem_favorite,
                    R.id.menuitem_post,
                    R.id.menuitem_chatting -> {
                        if (isGuest) {
                            Snackbar.make(this, R.string.all_guest_snackbar, Snackbar.LENGTH_SHORT).show()
                        } else {
                            val destinationId =
                                when (it.itemId) {
                                    R.id.menuitem_favorite -> R.id.favoriteFragment
                                    R.id.menuitem_post -> R.id.addPostFragment
                                    else -> R.id.chattingHomeFragment
                                }
                            binding.fragmentcontainerviewMain.findNavController().navigate(destinationId)
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
