package com.catchmate.presentation.view.home

import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.activity.MainActivity
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.view.components.FilterSheetType
import com.catchmate.presentation.viewmodel.MainViewModel
import com.catchmate.presentation.viewmodel.home.HomeEvent
import com.catchmate.presentation.viewmodel.home.HomeSideEffect
import com.catchmate.presentation.viewmodel.home.HomeViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseComposeFragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var notificationBadgeDrawable: BadgeDrawable? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        enableDoubleBackPressedExit = true

        (requireActivity() as MainActivity).refreshNotificationStatus()
    }

    @OptIn(ExperimentalBadgeUtils::class)
    fun updateNotificationBadge(hasUnreadNotification: Boolean) {
        notificationBadgeDrawable?.isVisible = hasUnreadNotification
        if (hasUnreadNotification) {
            if (notificationBadgeDrawable == null) {
                notificationBadgeDrawable = BadgeDrawable.create(requireContext())
                notificationBadgeDrawable?.apply {
                    backgroundColor = getColor(requireContext(), R.color.system_red) // 알림 색상 지정
                    clearNumber()
                    horizontalOffset = 40
                    verticalOffset = 30
                }
            }
            notificationBadgeDrawable?.let { badge ->
//                BadgeUtils.attachBadgeDrawable(badge, binding.layoutHeaderHome.imgbtnHeaderHomeNotification)
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        val uiState by homeViewModel.uiState.collectAsState()
        var showBottomSheet by remember { mutableStateOf<FilterSheetType?>(null) }

        val result = findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.get<Long>("deletedBoardId")

        LaunchedEffect(result) {
            result?.let { id ->
                homeViewModel.onEvent(HomeEvent.OnBoardDeleted(id))
                findNavController().currentBackStackEntry?.savedStateHandle?.remove<Long>("deletedBoardId")
            }
        }

        LaunchedEffect(Unit) {
            homeViewModel.sideEffect.collect { effect ->
                when (effect) {
                    HomeSideEffect.NavigateToLogin -> {
                        val navOptions =
                            NavOptions
                                .Builder()
                                .setPopUpTo(R.id.homeFragment, true)
                                .build()
                        val bundle = Bundle()
                        bundle.putInt("navigateCode", NAVIGATE_CODE_REISSUE)
                        findNavController().navigate(
                            R.id.action_homeFragment_to_loginFragment,
                            bundle,
                            navOptions
                        )
                    }

                    HomeSideEffect.NavigateToNotification -> {
                        if (mainViewModel.isGuestLogin.value == true) {
                            Snackbar.make(
                                requireView(),
                                R.string.all_guest_snackbar,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
                        }
                    }
                    HomeSideEffect.ShowClubBottomSheet -> {
                        showBottomSheet = FilterSheetType.Club(uiState.clubFilterData)
                    }
                    HomeSideEffect.ShowDatePickerBottomSheet -> {
                        showBottomSheet = FilterSheetType.Date(uiState.selectedDate)
                    }
                    HomeSideEffect.ShowMemberCountBottomSheet -> {
                        showBottomSheet = FilterSheetType.Member(uiState.memberFilterData)
                    }
                    is HomeSideEffect.NavigateToReadPost -> {
                        if (mainViewModel.isGuestLogin.value == true) {
                            Snackbar.make(
                                requireView(),
                                R.string.all_guest_snackbar,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            val bundle = Bundle()
                            bundle.putLong("boardId", effect.boardId)
                            findNavController().navigate(
                                R.id.action_homeFragment_to_readPostFragment,
                                bundle
                            )
                        }
                    }
                }
            }
        }

        HomeScreen(
            boardList = emptyList(),
            uiState = uiState,
            onEvent = homeViewModel::onEvent,
        )

        showBottomSheet?.let { type ->
            HomeBottomSheet(
                sheetType = type,
                onDismissRequest = { showBottomSheet = null },
                onDateApply = { dateStr ->
                    homeViewModel.onEvent(HomeEvent.OnDateFilterApplied(dateStr))
                    showBottomSheet = null
                },
                onClubApply = { clubIds ->
                    homeViewModel.onEvent(HomeEvent.OnClubFilterApplied(clubIds))
                    showBottomSheet = null
                },
                onMemberApply = { memberStr ->
                    homeViewModel.onEvent(HomeEvent.OnMemberFilterApplied(memberStr))
                    showBottomSheet = null
                },
                onReset = { filterSheetType ->
                    homeViewModel.onEvent(HomeEvent.OnFilterReset(filterSheetType))
                },
            )
        }
    }
}


