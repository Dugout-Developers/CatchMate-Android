package com.catchmate.presentation.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentAddPostBinding

class AddPostFragment : Fragment() {
    private var _binding: FragmentAddPostBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddPostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAddPostFooter.btnFooterOne.setText(R.string.posting)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
