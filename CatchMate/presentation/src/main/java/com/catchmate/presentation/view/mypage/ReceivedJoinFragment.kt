package com.catchmate.presentation.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentReceivedJoinBinding

class ReceivedJoinFragment : Fragment() {
    private var _binding: FragmentReceivedJoinBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReceivedJoinBinding.inflate(inflater, container, false)
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
        binding.layoutHeaderReceivedJoin.apply {
            tvHeaderTextTitle.setText(R.string.mypage_received_join)
            tvHeaderTextUnreadMessageCountBadge.visibility = View.VISIBLE // message 확인 여부에 따라 유동적으로 설정하기
        }
    }

//    private fun initEvent() {
//        binding.layoutHeaderReceivedJoin.tvHeaderTextTitle.setOnClickListener {
//            val dialog = ReceivedEnrollScrollDialogFragment()
//            dialog.show(parentFragmentManager, "ReceivedEnrollDialog")
//        }
//    }
}
