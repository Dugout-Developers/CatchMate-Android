package com.catchmate.presentation.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ActivityMainBinding
import com.catchmate.presentation.view.home.HomeFragment
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.catchmate.presentation.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import com.catchmate.presentation.viewmodel.MainViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val localDataViewModel: LocalDataViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private var chatBadgeDrawable: BadgeDrawable? = null

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
        observeChatNotifications()
    }

    private fun observeChatNotifications() {
        mainViewModel.getUnreadInfoResponse.observe(this) { info ->
            updateChatBadge(info.hasUnreadChat)
            updateHomeNotificationBadge(info.hasUnreadNotification)
        }
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
        val menuItemId =
            when (destinationId) {
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

    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalBadgeUtils::class)
    fun updateChatBadge(showBadge: Boolean) {
        val bottomNavigationMenuView = binding.bottomnavigationviewMain.getChildAt(0) as BottomNavigationMenuView

        // 채팅 메뉴 위치 찾기
        val chatMenuItemPosition = findMenuItemPosition(R.id.menuitem_chatting)
        val menuItemView = bottomNavigationMenuView.getChildAt(chatMenuItemPosition)

        if (showBadge) {
            if (chatBadgeDrawable == null) {
                chatBadgeDrawable = BadgeDrawable.create(this)
                chatBadgeDrawable?.apply {
                    backgroundColor = getColor(R.color.system_blue)
                    isVisible = true
                    clearNumber()
                    horizontalOffset = 100
                    verticalOffset = 50
                }
            } else {
                chatBadgeDrawable?.isVisible = true
            }

            // 메뉴 항목에 뱃지 부착
            chatBadgeDrawable?.let { badge ->
                BadgeUtils.attachBadgeDrawable(badge, menuItemView)
            }
        } else {
            // 뱃지 숨기기
            chatBadgeDrawable?.isVisible = false
        }
    }

    // 메뉴 ID에 해당하는 위치 찾기
    private fun findMenuItemPosition(menuItemId: Int): Int {
        val menu = binding.bottomnavigationviewMain.menu
        for (i in 0 until menu.size()) {
            if (menu.getItem(i).itemId == menuItemId) {
                return i
            }
        }
        // 기본값 - 채팅 메뉴 위치
        return 3
    }

    fun refreshNotificationStatus() {
        mainViewModel.getUnreadInfo()
    }

    private fun updateHomeNotificationBadge(hasUnreadNotification: Boolean) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentcontainerview_main) as NavHostFragment
        val homeFragment =
            navHostFragment.childFragmentManager.fragments.find { it is HomeFragment } as? HomeFragment
        homeFragment?.updateNotificationBadge(hasUnreadNotification)
    }
}
