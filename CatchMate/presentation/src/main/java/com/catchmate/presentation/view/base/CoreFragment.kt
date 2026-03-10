package com.catchmate.presentation.view.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.google.android.material.snackbar.Snackbar

abstract class CoreFragment : Fragment() {
    private var backPressedCallback: OnBackPressedCallback? = null
    protected var onBackPressedAction: (() -> Unit)? = null
    protected var enableDoubleBackPressedExit = false
    private var lastBackPressedTime: Long = 0L

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPressedCallback()
    }

    private fun setupBackPressedCallback() {
        backPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (enableDoubleBackPressedExit) {
                        handleDoubleBackToExit()
                    } else {
                        onBackPressedAction?.invoke() ?: findNavController().popBackStack()
                    }
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback as OnBackPressedCallback,
        )
    }

    private fun handleDoubleBackToExit() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressedTime < 2000) {
            requireActivity().moveTaskToBack(true) // 앱을 백그라운드로 이동시킴
        } else {
            lastBackPressedTime = currentTime
            Snackbar.make(requireView(), R.string.back_pressed_toast, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback?.remove()
    }
}
