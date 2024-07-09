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
        initHeader()
        initFooter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHeader() {
        binding.layoutAddPostHeader.run {
            imgbtnHeaderTextBack.setOnClickListener {
                // back
            }
            tvHeaderTextTitle.visibility = View.GONE
            tvHeaderTextSub.setText(R.string.temporary_storage)
        }
    }

    private fun initFooter() {
        binding.layoutAddPostFooter.btnFooterOne.setText(R.string.post_complete)
    }
}
