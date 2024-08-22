package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingHomeBinding

class ChattingHomeFragment : Fragment() {
    private var _binding: FragmentChattingHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChattingHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutHeaderChattingHome.apply {
            tvHeaderTextTitle.setText(R.string.chatting_home_title)
            imgbtnHeaderTextBack.visibility = View.GONE
        }
    }
}
