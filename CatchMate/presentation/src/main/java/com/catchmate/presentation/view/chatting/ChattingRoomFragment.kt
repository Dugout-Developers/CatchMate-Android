package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.domain.model.chatting.ChatMessageId
import com.catchmate.domain.model.chatting.ChatMessageInfo
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.enumclass.ChatMessageType
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingRoomBinding
import com.catchmate.presentation.databinding.LayoutApplicationDetailDialogBinding
import com.catchmate.presentation.databinding.LayoutChattingSideSheetBinding
import com.catchmate.presentation.databinding.LayoutSimpleDialogBinding
import com.catchmate.presentation.util.DateUtils.formatISODateTime
import com.catchmate.presentation.util.DateUtils.getCurrentTimeFormatted
import com.catchmate.presentation.util.ResourceUtil.setTeamViewResources
import com.catchmate.presentation.viewmodel.ChattingRoomViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.gmail.bishoybasily.stomp.lib.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.sidesheet.SideSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class ChattingRoomFragment : Fragment() {
    private var _binding: FragmentChattingRoomBinding? = null
    val binding get() = _binding!!

    private val chattingRoomViewModel: ChattingRoomViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private var chatRoomId: Long = -1L
    private var userId: Long = -1L
    private lateinit var chatListAdapter: ChatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatRoomId = getChatRoomId()
        Log.e("chatRoomId", chatRoomId.toString())
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
        initViewModel()
        chattingRoomViewModel.getChattingRoomInfo(chatRoomId)
        localDataViewModel.getUserId()
        initChatBox()
        connectToWebSocket()
        initSendBtn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chattingRoomViewModel.disposables.dispose()
        _binding = null
    }

    private fun getChatRoomId(): Long = arguments?.getLong("chatRoomId") ?: -1L

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
        chattingRoomViewModel.subscribeToChatRoom(chatRoomId).subscribe({ message ->
            Log.d("✅ Msg", message)
            // recycler view에 새로운 말풍선뷰 add
            val jsonObject = JSONObject(message)
            val messageType = jsonObject.getString("messageType")
            val senderId = jsonObject.getString("senderId").toLong()
            val content = jsonObject.getString("content")
            val chatMessageId = ChatMessageId(date = getCurrentTimeFormatted())
            val chatMessageInfo =
                ChatMessageInfo(
                    id = chatMessageId,
                    content = content,
                    senderId = senderId,
                    messageType = messageType,
                )
            Log.e("⭐️JSON 확인", "$messageType - $senderId - $content - ${chatMessageId.date}")
            chattingRoomViewModel.addChatMessage(chatMessageInfo)
        }, { error ->
            Log.e("Web Socket❌", "구독 중 오류 발생", error)
        })
    }

    private fun initViewModel() {
        chattingRoomViewModel.getChattingHistoryResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("👀observer", "work \n ${response.chatMessageInfoList.size}")
                chatListAdapter.submitList(response.chatMessageInfoList) {
                    // 리스트 갱신 후 콜백을 통해 최신 메시지로 스크롤 이동
                    binding.rvChattingRoomChatList.smoothScrollToPosition(0)
                }
            }
        }
        chattingRoomViewModel.getChattingCrewListResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                initRecyclerView()
            }
        }
        chattingRoomViewModel.chattingRoomInfo.observe(viewLifecycleOwner) { info ->
            if (info != null) {
                initChatRoomInfo(info)
                initHeader(info)
            }
        }
        chattingRoomViewModel.deleteChattingRoomResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.state) {
                    Log.d("채팅방 나가기 성공", "나가기 성공")
                    findNavController().popBackStack()
                } else {
                    Log.e("채팅방 오류", "나가기 실패")
                }
            }
        }
        localDataViewModel.userId.observe(viewLifecycleOwner) { id ->
            userId = id
            chattingRoomViewModel.getChattingCrewList(chatRoomId)
        }
    }

    private fun initRecyclerView() {
        Log.e("userID", userId.toString())
        chatListAdapter = ChatListAdapter(userId, chattingRoomViewModel.getChattingCrewListResponse.value?.userInfoList!!)
        binding.rvChattingRoomChatList.apply {
            adapter = chatListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        }
        chattingRoomViewModel.getChattingHistory(chatRoomId, 0)
    }

    private fun initChatRoomInfo(info: ChatRoomInfo) {
        binding.cgivChattingRoom.apply {
            val isCheerTeam = info.boardInfo.gameInfo.homeClubId == info.boardInfo.cheerClubId
            setHomeTeamImageView(
                info.boardInfo.gameInfo.homeClubId,
                isCheerTeam,
            )
            setAwayTeamImageView(
                info.boardInfo.gameInfo.awayClubId,
                !isCheerTeam,
            )
            val (date, time) = formatISODateTime(info.boardInfo.gameInfo.gameStartDate!!)
            setGameDateTextView(date)
            setGameTimeTextView(time)
            setGamePlaceTextView(info.boardInfo.gameInfo.location)
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
            }.toString()

            chattingRoomViewModel.sendChat(
                chatRoomId,
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

    private fun initHeader(info: ChatRoomInfo) {
        binding.layoutHeaderChattingRoom.apply {
            imgbtnHeaderMenuMenu.setOnClickListener {
                val sideSheetDialog = SideSheetDialog(requireContext())
                val sideSheetBinding = LayoutChattingSideSheetBinding.inflate(layoutInflater)
                sideSheetDialog.setContentView(sideSheetBinding.root)

                sideSheetBinding.apply {
                    // 게시글 정보
                    val dateTimePair = formatISODateTime(info.boardInfo.gameInfo.gameStartDate!!)
                    tvSideSheetDate.text = dateTimePair.first
                    tvSideSheetTime.text = dateTimePair.second
                    tvSideSheetPlace.text = info.boardInfo.gameInfo.location
                    tvSideSheetCountBadge.text = "${info.participantCount}/${info.boardInfo.maxPerson}"
                    tvSideSheetTitle.text = info.boardInfo.title
                    val isCheerTeam = info.boardInfo.cheerClubId == info.boardInfo.gameInfo.homeClubId
                    setTeamViewResources(
                        info.boardInfo.gameInfo.homeClubId,
                        isCheerTeam,
                        ivSideSheetHomeTeam,
                        ivSideSheetHomeLogo,
                        "chattingRoom",
                        requireContext(),
                    )
                    setTeamViewResources(
                        info.boardInfo.gameInfo.awayClubId,
                        !isCheerTeam,
                        ivSideSheetAwayTeam,
                        ivSideSheetAwayLogo,
                        "chattingRoom",
                        requireContext(),
                    )

                    // 참여자 정보
                    var crewAdapter = ChattingRoomSideSheetCrewAdapter(userId, info.boardInfo.userInfo.userId)
                    rvSideSheetParticipantList.apply {
                        adapter = crewAdapter
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                    crewAdapter.submitList(chattingRoomViewModel.getChattingCrewListResponse.value?.userInfoList)

                    // 버튼 기능
                    ivSideSheetLeaveChattingRoom.setOnClickListener {
                        sideSheetDialog.dismiss()
                        showChattingRoomLeaveDialog()
                    }
                    if (userId == info.boardInfo.userInfo.userId) {
                        ivSideSheetSettings.visibility = View.VISIBLE
                        ivSideSheetSettings.setOnClickListener {
                            // 채팅방 이미지 url, 참여자 목록, 로그인 유저 id, 게시글 작성자 id 넘김
                            val bundle = Bundle().apply {
                                putString("chattingRoomImage", info.chatRoomImage)
                                putParcelable("chattingCrewList", chattingRoomViewModel.getChattingCrewListResponse.value)
                                putLong("loginUserId", userId)
                                putLong("writerId", info.boardInfo.userInfo.userId)
                            }
                            findNavController().navigate(R.id.action_chattingRoomFragment_to_chattingSettingFragment, bundle)
                            sideSheetDialog.dismiss()
                        }
                    } else {
                        ivSideSheetSettings.visibility = View.GONE
                    }
                    toggleSideSheetChattingRoomNotification.setOnCheckedChangeListener { buttonView, isChecked ->
                        // 토글 상태에 따른 알림 설정 api 호출
                    }
                }

                sideSheetDialog.show()
            }
            imgbtnHeaderMenuBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvHeaderMenuTitle.text = info.boardInfo.title
            tvHeaderMenuMemberCount.text = info.participantCount.toString()
        }
    }

    private fun showChattingRoomLeaveDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutSimpleDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        dialogBinding.apply {
            tvSimpleDialogTitle.setText(R.string.chatting_leave_dialog_title)

            tvSimpleDialogNegative.apply {
                setText(R.string.dialog_button_cancel)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            tvSimpleDialogPositive.apply {
                setText(R.string.chatting_leave_dialog_positive_button)
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.brand500),
                )
                setOnClickListener {
                    chattingRoomViewModel.deleteChattingRoom(chatRoomId)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
}
