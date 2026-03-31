package com.catchmate.presentation.view.notification

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.view.base.BaseComposeFragment
import com.catchmate.presentation.viewmodel.notification.NotificationEvent
import com.catchmate.presentation.viewmodel.notification.NotificationSideEffect
import com.catchmate.presentation.viewmodel.notification.NotificationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :
    BaseComposeFragment() {
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        notificationViewModel.onEvent(NotificationEvent.InitData)
    }

//    override fun onNotificationItemClick(
//        notificationId: Long,
//        currentPos: Int,
//        acceptStatus: String?,
//        chatRoomId: Long?,
//        inquiryId: Long?,
//    ) {
//        clickedItemPos = currentPos
//        notificationViewModel.getReceivedNotification(notificationId)
//        if (inquiryId == null) {
//            if (acceptStatus == AcceptState.PENDING.name) { // pending
//                findNavController().navigate(R.id.action_notificationFragment_to_receivedJoinFragment)
//            } else if (acceptStatus == AcceptState.ACCEPTED.name) { // accepted
//                val bundle = Bundle()
//                bundle.putLong("chatRoomId", chatRoomId!!)
//                findNavController().navigate(R.id.action_notificationFragment_to_chattingRoomFragment, bundle)
//            } else if (acceptStatus == AcceptState.ALREADY_REJECTED.name) { // already_rejected
//                Snackbar.make(requireView(), R.string.notification_already_rejected_snackbar, Snackbar.LENGTH_SHORT).show()
//            } else {
//                Snackbar.make(requireView(), R.string.notification_already_accepted_snackbar, Snackbar.LENGTH_SHORT).show()
//            }
//        } else {
//            val bundle = Bundle()
//            bundle.putLong("inquiryId", inquiryId)
//            findNavController().navigate(R.id.action_notificationFragment_to_serviceCenterAnswerFragment, bundle)
//        }
//    }

    @Composable
    override fun ComposeContent() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val uiState by notificationViewModel.uiState.collectAsState()

        LaunchedEffect(lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notificationViewModel.sideEffect.collect { effect ->
                    when (effect) {
                        NotificationSideEffect.NavigateToBack -> {
                            findNavController().popBackStack()
                        }

                        NotificationSideEffect.NavigateToDetail -> {
                            // API 수정 후 반영 필요
                        }

                        NotificationSideEffect.NavigateToLogin -> {
                            val navOptions =
                                NavOptions
                                    .Builder()
                                    .setPopUpTo(R.id.notificationFragment, true)
                                    .build()
                            val bundle = Bundle()
                            bundle.putInt("navigateCode", NAVIGATE_CODE_REISSUE)
                            findNavController().navigate(R.id.action_notificationFragment_to_loginFragment, bundle, navOptions)
                        }

                        is NotificationSideEffect.ShowSnackBar -> {
                            Snackbar.make(requireView(), getString(effect.stringResource), Snackbar.LENGTH_SHORT)
                        }
                    }
                }
            }
        }

        NotificationScreen(
            uiState = uiState,
            onEvent = notificationViewModel::onEvent,
        )
    }
}
