package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentMyPageBinding
import com.catchmate.presentation.util.AgeUtils
import com.catchmate.presentation.util.ClubUtils
import com.catchmate.presentation.util.GenderUtils
import com.catchmate.presentation.util.ResourceUtil.convertTeamColor
import com.catchmate.presentation.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    val binding get() = _binding!!

    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
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

    private fun initHeader() {
        binding.layoutHeaderMyPage.apply {
            tvSettingHeaderTitle.setText(R.string.mypage_title)
            imgbtnSettingHeaderSetting.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("email", myPageViewModel.userProfile.value?.email)
                findNavController().navigate(R.id.action_myPageFragment_to_myPageSettingFragment, bundle)
            }
        }
    }

    private fun initProfile(userInfo: GetUserProfileResponse) {
        binding.viewMyPageProfile.binding.apply {
            Glide
                .with(this@MyPageFragment)
                .load(userInfo.profileImageUrl)
                .into(ivMyPageUserProfile)
            tvMyPageUserProfileNickname.text = userInfo.nickName
            tvMyPageUserProfileTeamBadge.text = ClubUtils.convertClubIdToName(userInfo.favoriteClub.id)

            DrawableCompat
                .setTint(
                    tvMyPageUserProfileTeamBadge.background,
                    convertTeamColor(
                        requireContext(),
                        userInfo.favoriteClub.id,
                        true,
                        "mypage",
                    ),
                )

            if (userInfo.watchStyle.isNullOrEmpty()) {
                tvMyPageUserProfileCheerStyleBadge.visibility = View.GONE
            } else {
                tvMyPageUserProfileCheerStyleBadge.text = userInfo.watchStyle
            }
            tvMyPageUserProfileGenderBadge.text = GenderUtils.convertBoardGender(requireContext(), userInfo.gender)
            tvMyPageUserProfileAgeBadge.text = AgeUtils.convertBirthDateToAge(userInfo.birthDate)
        }
    }

    private fun initViewModel() {
        myPageViewModel.getUserProfile()
        myPageViewModel.getEnrollNewCount()
        myPageViewModel.userProfile.observe(viewLifecycleOwner) { response ->
            initProfile(response)
        }
        myPageViewModel.newCount.observe(viewLifecycleOwner) { response ->
            if (response.newEnrollCount == 0) {
                binding.tvMyPageReceivedJoinUnreadCount.visibility = View.INVISIBLE
            } else {
                binding.tvMyPageReceivedJoinUnreadCount.apply {
                    visibility = View.VISIBLE
                    text = response.newEnrollCount.toString()
                }
            }
        }
    }

    private fun initViews() {
        binding.apply {
            viewMyPageProfile.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("userInfo", myPageViewModel.userProfile.value)
                findNavController().navigate(R.id.action_myPageFragment_to_editProfileFragment, bundle)
            }
            tvMyPageMyPost.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("userInfo", myPageViewModel.userProfile.value)
                findNavController().navigate(R.id.action_myPageFragment_to_myPostFragment, bundle)
            }
            tvMyPageSentJoin.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_sentJoinFragment)
            }
            layoutMyPageReceivedJoin.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("newCount", myPageViewModel.newCount.value?.newEnrollCount ?: 0)
                findNavController().navigate(R.id.action_myPageFragment_to_receivedJoinFragment, bundle)
            }
        }
    }
}
