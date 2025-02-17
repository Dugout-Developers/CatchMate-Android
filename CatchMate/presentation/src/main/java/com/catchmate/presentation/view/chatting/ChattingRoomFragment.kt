package com.catchmate.presentation.view.chatting

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.enumclass.ChatMessageType
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingRoomBinding
import com.catchmate.presentation.util.DateUtils.formatISODateTime
import com.catchmate.presentation.viewmodel.ChattingRoomViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.gmail.bishoybasily.stomp.lib.Event
import com.google.android.material.sidesheet.SideSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.time.LocalDateTime

@AndroidEntryPoint
class ChattingRoomFragment : Fragment() {
    private var _binding: FragmentChattingRoomBinding? = null
    val binding get() = _binding!!

    private val chattingRoomViewModel: ChattingRoomViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private var chatRoomInfo: ChatRoomInfo? = null
    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatRoomInfo = getChatRoomInfo()
        Log.e("chatRoomInfo", chatRoomInfo?.chatRoomId.toString())
    }

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
        initChatRoomInfo()
        getUserId()
        connectToWebSocket()
        initSendBtn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chattingRoomViewModel.disposables.dispose()
        _binding = null
    }

    private fun getChatRoomInfo(): ChatRoomInfo? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("chatRoomInfo", ChatRoomInfo::class.java)
        } else {
            arguments?.getParcelable("chatRoomInfo") as ChatRoomInfo?
        }

    private fun getUserId() {
        localDataViewModel.getUserId()
        localDataViewModel.userId.observe(viewLifecycleOwner) { id ->
            userId = id
        }
    }

    private fun connectToWebSocket() {
        chattingRoomViewModel.connectToWebSocket().subscribe({ event ->
            when (event.type) {
                Event.Type.OPENED -> {
                    Log.d("Web Socket✅", "연결 성공")
                    handleWebSocketOpened()
                }
                Event.Type.CLOSED -> Log.d("Web Socket💤", "연결 해제")
                Event.Type.ERROR -> Log.e("Web Socket❌", "에러 발생")
                else -> {}
            }
        }, { error ->
            Log.e("Web Socket❌", "오류 발생", error)
        })
    }

    private fun handleWebSocketOpened() {
        chattingRoomViewModel.subscribeToChatRoom(chatRoomInfo?.chatRoomId!!).subscribe({ message ->
            Log.d("✅ Msg", message)
        }, { error ->
            Log.e("Web Socket❌", "구독 중 오류 발생", error)
        })
    }

    private fun initChatRoomInfo() {
        binding.cgivChattingRoom.apply {
            val isCheerTeam = chatRoomInfo?.boardInfo?.gameInfo?.homeClubId!! == chatRoomInfo?.boardInfo?.cheerClubId!!
            setHomeTeamImageView(
                chatRoomInfo?.boardInfo?.gameInfo?.homeClubId!!,
                isCheerTeam,
            )
            setAwayTeamImageView(
                chatRoomInfo?.boardInfo?.gameInfo?.awayClubId!!,
                !isCheerTeam,
            )
            val (date, time) = formatISODateTime(chatRoomInfo?.boardInfo?.gameInfo?.gameStartDate!!)
            setGameDateTextView(date)
            setGameTimeTextView(time)
            setGamePlaceTextView(chatRoomInfo?.boardInfo?.gameInfo?.location!!)
        }
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
            val message = JSONObject().apply {
                put("messageType", ChatMessageType.TALK.name)
                put("content", binding.edtChattingRoomChatBox.text.toString())
                put("senderId", userId)
                put("sendTime", LocalDateTime.now().toString())
            }.toString()

            chattingRoomViewModel.sendChat(
                chatRoomInfo?.chatRoomId!!,
                message,
            ).subscribe ({ isSend ->
                binding.edtChattingRoomChatBox.setText("")
                if (isSend) {
                    Log.d("Web Socket📬", "메시지 전달")
                } else {
                    Log.e("Web Socket😩", "메시지 전달 실패")
                }
            }, { error ->
                Log.e("Web Socket✉️❌", "메시지 전송 실패", error)
            })
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
            imgbtnHeaderMenuBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvHeaderMenuTitle.text = chatRoomInfo?.boardInfo?.title
            tvHeaderMenuMemberCount.text = chatRoomInfo?.participantCount.toString()
        }
    }
}
