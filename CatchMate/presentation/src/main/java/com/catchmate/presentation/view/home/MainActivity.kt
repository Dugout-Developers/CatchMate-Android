package com.catchmate.presentation.view.home

import android.Manifest
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ActivityMainBinding
import com.catchmate.presentation.viewmodel.LocalDataViewModel
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
        initNavController()
        initBottomNavigationView()

        getTokens()
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

    private fun getTokens() {
        localDataViewModel.getAccessToken()
        localDataViewModel.accessToken.observe(this) { accessToken ->
            if (accessToken.isNullOrEmpty()) {
                Log.e("메인a", "accesstoken null or empty")
                binding.fragmentcontainerviewMain.findNavController().navigate(R.id.loginFragment)
            } else {
                Log.e("메인a", "accesstoken not null or empty")
                binding.fragmentcontainerviewMain.findNavController().navigate(R.id.homeFragment)
                binding.bottomnavigationviewMain.selectedItemId = R.id.menuitem_home
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
                        updateBottomNavigationSelection(destination.id)
                    }
                    else -> {
                        bottomnavigationviewMain.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun updateBottomNavigationSelection(destinationId: Int) {
        val menuItemId = when (destinationId) {
            R.id.homeFragment -> R.id.menuitem_home
            R.id.favoriteFragment -> R.id.menuitem_favorite
            R.id.addPostFragment -> R.id.menuitem_post
            R.id.chattingHomeFragment -> R.id.menuitem_chatting
            R.id.myPageFragment -> R.id.menuitem_mypage
            else -> null
        }
        menuItemId?.let {
            // 선택 리스너를 일시적으로 제거하지 않으면 무한 루프가 발생 가능
            binding.bottomnavigationviewMain.setOnItemSelectedListener(null)
            binding.bottomnavigationviewMain.selectedItemId = it
            initBottomNavigationView() // 리스너 다시 설정
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
                        binding.fragmentcontainerviewMain.findNavController().navigate(R.id.favoriteFragment)
                    }
                    R.id.menuitem_post -> {
                        binding.fragmentcontainerviewMain.findNavController().navigate(R.id.addPostFragment)
                    }
                    R.id.menuitem_chatting -> {
                        binding.fragmentcontainerviewMain.findNavController().navigate(R.id.chattingHomeFragment)
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
