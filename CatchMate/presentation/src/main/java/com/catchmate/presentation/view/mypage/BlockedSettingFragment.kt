package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentBlockedSettingBinding
import com.catchmate.presentation.databinding.LayoutSimpleDialogBinding
import com.catchmate.presentation.interaction.OnBlockedUserSelectedListener
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.BlockedSettingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockedSettingFragment :
    BaseFragment<FragmentBlockedSettingBinding>(FragmentBlockedSettingBinding::inflate),
    OnBlockedUserSelectedListener {
    private lateinit var blockedUserAdapter: BlockedUserListAdapter
    private val blockedSettingViewModel: BlockedSettingViewModel by viewModels()
    private var deletedUserId = -1L

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        blockedSettingViewModel.getBlockedUserList()
        initView()
    }

    private fun initView() {
        binding.apply {
            layoutHeaderBlockedSetting.tvHeaderTextTitle.text = getString(R.string.mypage_setting_block_setting)
            layoutHeaderBlockedSetting.imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
            blockedUserAdapter = BlockedUserListAdapter(this@BlockedSettingFragment)
            rvBlockedUserListBlockedSetting.apply {
                adapter = blockedUserAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun initViewModel() {
        blockedSettingViewModel.getBlockedUserListResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                blockedUserAdapter.submitList(response.userInfoList)
            }
        }
        blockedSettingViewModel.deleteBlockedUserResponse.observe(viewLifecycleOwner) { response ->
            if (response.state) {
                blockedSettingViewModel.deleteUserFromList(deletedUserId)
            }
        }
    }

    override fun onBlockedUserSelected(
        pos: Int,
        userId: Long,
        nickname: String,
    ) {
        deletedUserId = userId
        showBlockDeleteDialog(userId, nickname)
    }

    private fun showBlockDeleteDialog(
        userId: Long,
        nickname: String,
    ) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutSimpleDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        dialogBinding.apply {
            val title = getString(R.string.mypage_setting_block_dialog_title)
            tvSimpleDialogTitle.text = title.format(nickname)
            tvSimpleDialogNegative.apply {
                setText(R.string.dialog_button_cancel)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            tvSimpleDialogPositive.apply {
                setText(R.string.mypage_setting_block_dialog_pov_btn)
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.brand500),
                )
                setOnClickListener {
                    blockedSettingViewModel.deleteBlockedUser(userId)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
}
