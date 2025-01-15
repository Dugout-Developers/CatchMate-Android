package com.catchmate.presentation.view.mypage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentEditProfileBinding
import com.catchmate.presentation.interaction.OnEditProfileTeamSelectedListener
import com.catchmate.presentation.util.ClubUtils.convertClubIdToName
import com.catchmate.presentation.viewmodel.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment(), OnEditProfileTeamSelectedListener {
    private var _binding: FragmentEditProfileBinding? = null
    val binding get() = _binding!!

    private var userInfo: GetUserProfileResponse? = null
    private val editProfileViewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = getUserInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initViewModel()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserInfo(): GetUserProfileResponse? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("userInfo", GetUserProfileResponse::class.java)
        } else {
            arguments?.getParcelable("userInfo") as GetUserProfileResponse?
        }

    private fun initHeader() {
        binding.layoutHeaderEditProfile.apply {
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvHeaderTextTitle.text = getString(R.string.edit_profile_title)
        }
    }

    private fun initViewModel() {
        editProfileViewModel.setNickName(userInfo?.nickName!!)
        editProfileViewModel.nickName.observe(viewLifecycleOwner) { str ->
            binding.edtEditProfileNickname.setText(str)
        }
        editProfileViewModel.setProfileImage(userInfo?.profileImageUrl!!)
        editProfileViewModel.profileImage.observe(viewLifecycleOwner) { url ->
            Glide
                .with(this@EditProfileFragment)
                .load(url)
                .error(R.drawable.temporary_profile)
                .into(binding.ivEditProfileThumbnail)
        }
        editProfileViewModel.setCheerClub(userInfo?.favoriteClub?.id!!)
        editProfileViewModel.cheerClub.observe(viewLifecycleOwner) { id ->
            binding.tvEditProfileCheerClub.text = convertClubIdToName(id)
        }
        editProfileViewModel.setWatchStyle(userInfo?.watchStyle)
        editProfileViewModel.watchStyle.observe(viewLifecycleOwner) { str ->
            str?.let {
                binding.tvEditProfileWatchStyle.text = it
            }
        }
    }

    private fun initViews() {
        binding.tvEditProfileCheerClub.setOnClickListener {
            val cheerClubBottomSheet = EditProfileTeamBottomSheetFragment(editProfileViewModel.cheerClub.value!!, this@EditProfileFragment)
            cheerClubBottomSheet.show(requireActivity().supportFragmentManager, cheerClubBottomSheet.tag)
        }
        binding.tvEditProfileWatchStyle.setOnClickListener {
            val watchStyleBottomSheet = EditProfileWatchStyleBottomSheetFragment()
            watchStyleBottomSheet.show(requireActivity().supportFragmentManager, watchStyleBottomSheet.tag)
        }
    }

    override fun onTeamSelected(cludId: Int) {
        editProfileViewModel.setCheerClub(cludId)
    }
}
