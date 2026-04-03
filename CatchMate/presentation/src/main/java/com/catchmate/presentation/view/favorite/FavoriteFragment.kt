package com.catchmate.presentation.view.favorite

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
import com.catchmate.presentation.viewmodel.favorite.FavoriteEvent
import com.catchmate.presentation.viewmodel.favorite.FavoriteSideEffect
import com.catchmate.presentation.viewmodel.favorite.FavoriteUiState
import com.catchmate.presentation.viewmodel.favorite.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseComposeFragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        enableDoubleBackPressedExit = true
        favoriteViewModel.onEvent(FavoriteEvent.InitData)
    }

    @Composable
    override fun ComposeContent() {
        val uiState by favoriteViewModel.uiState.collectAsState()
        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteViewModel.sideEffect.collect { effect ->
                    when (effect) {
                        FavoriteSideEffect.NavigateToLogin -> {
                            val navOptions =
                                NavOptions
                                    .Builder()
                                    .setPopUpTo(R.id.favoriteFragment, true)
                                    .build()
                            val bundle = Bundle()
                            bundle.putInt("navigateCode", NAVIGATE_CODE_REISSUE)
                            findNavController().navigate(R.id.action_favoriteFragment_to_loginFragment, bundle, navOptions)
                        }

                        is FavoriteSideEffect.NavigateToReadPost -> {
                            val bundle = Bundle()
                            bundle.putLong("boardId", effect.boardId)
                            findNavController().navigate(R.id.action_favoriteFragment_to_readPostFragment, bundle)
                        }

                        FavoriteSideEffect.ShowSnackBar -> {
                            Snackbar.make(requireView(), R.string.all_component_error_msg, Snackbar.LENGTH_SHORT)
                        }
                    }
                }
            }
        }

        FavoriteScreen(
            uiState = uiState,
            onEvent = favoriteViewModel::onEvent,
        )
    }
}
