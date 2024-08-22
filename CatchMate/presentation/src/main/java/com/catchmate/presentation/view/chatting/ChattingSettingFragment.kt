package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingSettingBinding

class ChattingSettingFragment : Fragment() {
    private var _binding: FragmentChattingSettingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChattingSettingBinding.inflate(inflater, container, false)
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
        binding.layoutHeaderChattingSetting.apply {
            tvHeaderTextTitle.setText(R.string.chatting_setting_title)
            imgbtnHeaderTextBack.setImageResource(R.drawable.vec_all_close_20dp)
        }
    }
}
