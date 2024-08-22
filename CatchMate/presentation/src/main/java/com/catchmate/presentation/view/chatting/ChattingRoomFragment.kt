package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingRoomBinding
import com.google.android.material.sidesheet.SideSheetDialog

class ChattingRoomFragment : Fragment() {
    private var _binding: FragmentChattingRoomBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChattingRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initChatBox()
        initHeader()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initChatBox() {
        binding.edtChattingRoomChatBox.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    binding.btnChattingRoomChatBoxSend.isEnabled = count != 0
                }

                override fun afterTextChanged(s: Editable?) {
                }
            },
        )
    }

    private fun initSendBtn() {
        binding.btnChattingRoomChatBoxSend.setOnClickListener {
            // 전송과 동시에 edt 포커스를 잃게 하고 softkey 내리는 처리
        }
    }

    private fun initHeader() {
        binding.layoutHeaderChattingRoom.apply {
            imgbtnHeaderMenuMenu.setOnClickListener {
                val sideSheetDialog = SideSheetDialog(requireContext())
                sideSheetDialog.setContentView(R.layout.layout_chatting_side_sheet)
                sideSheetDialog.show()
                // 안 보이는 묹제
            }
        }
    }
}
