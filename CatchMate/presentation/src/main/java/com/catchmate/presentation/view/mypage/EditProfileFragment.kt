package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutHeaderEditProfile.apply {
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvHeaderTextTitle.text = getString(R.string.edit_profile_title)
        }
    }
}
