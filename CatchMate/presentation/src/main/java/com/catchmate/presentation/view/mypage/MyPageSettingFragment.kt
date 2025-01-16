package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentMyPageSettingBinding

class MyPageSettingFragment : Fragment() {
    private var _binding: FragmentMyPageSettingBinding? = null
    val binding get() = _binding!!

    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = arguments?.getString("email") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyPageSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        initMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutHeaderMyPageSetting.apply {
            tvHeaderTextTitle.setText(R.string.mypage_setting)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initMenu() {
        binding.tvMyPageSettingUserInfo.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("email", email)
            findNavController().navigate(R.id.action_myPageSettingFragment_to_accountInfoFragment, bundle)
        }
    }
}
