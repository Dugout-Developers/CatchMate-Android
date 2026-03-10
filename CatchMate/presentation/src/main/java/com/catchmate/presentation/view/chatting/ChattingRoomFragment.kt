package com.catchmate.presentation.view.chatting

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.chatting.PutChattingRoomAlarmRequest
import com.catchmate.domain.model.enumclass.ChatMessageType
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingRoomBinding
import com.catchmate.presentation.databinding.LayoutChattingSideSheetBinding
import com.catchmate.presentation.databinding.LayoutSimpleDialogBinding
import com.catchmate.presentation.util.DateUtils.formatISODateTime
import com.catchmate.presentation.util.ReissueUtil.NAVIGATE_CODE_REISSUE
import com.catchmate.presentation.util.ResourceUtil.setTeamViewResources
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ChattingRoomViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.sidesheet.SideSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class ChattingRoomFragment : BaseFragment<FragmentChattingRoomBinding>(FragmentChattingRoomBinding::inflate) {
    private val chattingRoomViewModel: ChattingRoomViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private var userId: Long = -1L
    private var lastMessageId: Long? = null
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
    private val isPendingIntent by lazy { arguments?.getBoolean("isPendingIntent") == true }
    private var isNotificationEnabled = false
    private lateinit var chatListAdapter: ChatListAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        localDataViewModel.getUserId()
        initViewModel()
        getChatRoomInfo()
        initChatBox()
        initSendBtn()
        onBackPressedAction = { setOnBackPressedAction() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chattingRoomViewModel.topic?.dispose()
        chattingRoomViewModel.stompClient?.disconnect()
    }

    private fun getChatRoomInfo() {
        val chatRoomInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("chatRoomInfo", ChatRoomInfo::class.java)
            } else {
                arguments?.getParcelable("chatRoomInfo")
            }
        chatRoomInfo?.let { info ->
            chattingRoomViewModel.setChatRoomInfo(info)
        }
    }

    private fun initViewModel() {
        chattingRoomViewModel.getChattingMessagesResponse.observe(viewLifecycleOwner) { response ->
            if (response.isEmpty()) {
                Log.d("빈 채팅방 목록", "empty")
            } else {
                Log.i("👀observer", "work \n ${response.size}")
                if (isApiCalled) {
                    val currentList = chatListAdapter.currentList.toMutableList()
                    Log.d("IS API CALLED", "🅾️")
                    currentList.addAll(response)
                    chatListAdapter.submitList(currentList)
                    isApiCalled = false
                } else {
                    chatListAdapter.submitList(response) {
                        // 수신 메시지 추가 후 콜백을 통해 최신 메시지로 스크롤 이동
                        binding.rvChattingRoomChatList.smoothScrollToPosition(0)
                        isApiCalled = false
                    }
                }
                isLastPage = response.size < 20
                isLoading = false
            }
        }
        chattingRoomViewModel.getChattingCrewListResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                initRecyclerView(response)
            }
        }
        chattingRoomViewModel.chattingRoomInfo.observe(viewLifecycleOwner) { info ->
            if (info != null) {
                isNotificationEnabled = info.notificationOn
                initChatRoomInfo(info)
                initHeader(info)
            }
        }
        chattingRoomViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.chattingRoomFragment, true)
                        .build()
                val bundle = Bundle()
                bundle.putInt("navigateCode", NAVIGATE_CODE_REISSUE)
                findNavController().navigate(R.id.action_chattingRoomFragment_to_loginFragment, bundle, navOptions)
            }
        }
        chattingRoomViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
        localDataViewModel.userId.observe(viewLifecycleOwner) { id ->
            userId = id
            localDataViewModel.getAccessToken()
            chattingRoomViewModel.getChattingCrewList(chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1)
        }
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { token ->
            chattingRoomViewModel.connectToWebSocket(chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1, token)
        }
        chattingRoomViewModel.isMessageSent.observe(viewLifecycleOwner) { isSent ->
            if (isSent) {
                binding.edtChattingRoomChatBox.setText("")
            } else {
                showAlertSnackbar(R.string.chatting_message_send_fail)
            }
        }
        chattingRoomViewModel.isLeft.observe(viewLifecycleOwner) { isSent ->
            if (isSent) {
                Log.d("채팅방 나가기 성공", "나가기 성공")
                val chatRoomId = chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1
                setFragmentResult("deleteChattingRoomResultKey", bundleOf("chatRoomId" to chatRoomId))
                findNavController().popBackStack()
            } else {
                Log.d("채팅방 오류", "나가기 실패")
                showAlertSnackbar(R.string.chatting_leave_room_fail)
            }
        }
        chattingRoomViewModel.isInstability.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                showAlertSnackbar(R.string.chatting_connect_instability)
            }
        }
    }

    private fun initRecyclerView(list: List<GetChattingCrewListResponse>) {
        Log.i("userID", userId.toString())
        chatListAdapter = ChatListAdapter(userId, list)
        binding.rvChattingRoomChatList.apply {
            adapter = chatListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(
                        recyclerView: RecyclerView,
                        dx: Int,
                        dy: Int,
                    ) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager)
                                .findLastCompletelyVisibleItemPosition()
                        val itemTotalCount = recyclerView.adapter!!.itemCount

                        if (lastVisibleItemPosition + 1 >= itemTotalCount && !isLastPage && !isLoading) {
                            if (chatListAdapter.currentList.isNotEmpty() &&
                                lastVisibleItemPosition >= 0 &&
                                lastVisibleItemPosition < chatListAdapter.currentList.size
                            ) {
                                lastMessageId = chatListAdapter.currentList[lastVisibleItemPosition].messageId
                                getChattingHistory()
                            }
                        }
                    }
                },
            )
        }
        if (isFirstLoad) {
            getChattingHistory()
            isFirstLoad = false
        }
    }

    private fun getChattingHistory() {
        Log.i("api 호출", "호출 $isLoading $isLastPage")
        if (isLoading || isLastPage) return
        isLoading = true
        chattingRoomViewModel.getChattingMessages(
            chatRoomId = chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1,
            lastMessageId = lastMessageId,
            userId = userId,
        )
        isApiCalled = true
    }

    private fun initChatRoomInfo(info: ChatRoomInfo) {
        binding.cgivChattingRoom.apply {
            val isCheerTeam =
                info.board.gameResponse.homeClub
                    ?.clubId == info.board.cheerClub.clubId
            setHomeTeamImageView(
                info.board.gameResponse.homeClub
                    ?.clubId ?: 0,
                isCheerTeam,
            )
            setAwayTeamImageView(
                info.board.gameResponse.awayClub
                    ?.clubId ?: 0,
                !isCheerTeam,
            )
            val (date, time) = formatISODateTime(info.board.gameResponse.gameStartDate!!)
            setGameDateTextView(date)
            setGameTimeTextView(time)
            setGamePlaceTextView(info.board.gameResponse.location!!)
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
            val request =
                JSONObject()
                    .apply {
                        put("chatRoomId", chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1)
                        put("content", binding.edtChattingRoomChatBox.text.toString())
                        put("messageType", ChatMessageType.TEXT)
                    }.toString()
            chattingRoomViewModel.sendMessage(request)
        }
    }

    private fun setOnBackPressedAction() {
        if (isPendingIntent) {
            val navOptions =
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.chattingRoomFragment, true)
                    .build()
            findNavController().navigate(R.id.action_chattingRoomFragment_to_homeFragment, null, navOptions)
        } else {
            findNavController().popBackStack()
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
                    val dateTimePair = formatISODateTime(info.board.gameResponse.gameStartDate!!)
                    tvSideSheetDate.text = dateTimePair.first
                    tvSideSheetTime.text = dateTimePair.second
                    tvSideSheetPlace.text = info.board.gameResponse.location
                    tvSideSheetCountBadge.text = "${info.board.currentPerson}/${info.board.maxPerson}"
                    tvSideSheetTitle.text = info.board.title
                    val isCheerTeam =
                        info.board.cheerClub.clubId ==
                            info.board.gameResponse.homeClub
                                ?.clubId
                    setTeamViewResources(
                        info.board.gameResponse.homeClub
                            ?.clubId ?: 0,
                        isCheerTeam,
                        ivSideSheetHomeTeam,
                        ivSideSheetHomeLogo,
                        "chattingRoom",
                        requireContext(),
                    )
                    setTeamViewResources(
                        info.board.gameResponse.awayClub
                            ?.clubId ?: 0,
                        !isCheerTeam,
                        ivSideSheetAwayTeam,
                        ivSideSheetAwayLogo,
                        "chattingRoom",
                        requireContext(),
                    )

                    // 참여자 정보
                    var crewAdapter = ChattingCrewListAdapter(userId, info.board.userResponse.userId, "chattingRoom")
                    rvSideSheetParticipantList.apply {
                        adapter = crewAdapter
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    }
                    crewAdapter.submitList(
                        (
                            chattingRoomViewModel.getChattingCrewListResponse.value
                                ?: emptyList<GetChattingCrewListResponse>()
                        ),
                    )

                    // 버튼 기능
                    ivSideSheetLeaveChattingRoom.setOnClickListener {
                        sideSheetDialog.dismiss()
                        showChattingRoomLeaveDialog()
                    }
                    if (userId == info.board.userResponse.userId) {
                        ivSideSheetSettings.visibility = View.VISIBLE
                        ivSideSheetSettings.setOnClickListener {
                            // 채팅방 이미지 url, 참여자 목록, 로그인 유저 id, 게시글 작성자 id, 채팅방 id 넘김
                            val bundle =
                                Bundle().apply {
                                    putString("chattingRoomImage", info.chatRoomImageUrl)
                                    putParcelableArrayList(
                                        "chattingCrewList",
                                        chattingRoomViewModel.getChattingCrewListResponse.value as ArrayList<out Parcelable?>?,
                                    )
                                    putLong("loginUserId", userId)
                                    putLong("writerId", info.board.userResponse.userId)
                                    putLong("chatRoomId", chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1)
                                }
                            findNavController().navigate(R.id.action_chattingRoomFragment_to_chattingSettingFragment, bundle)
                            sideSheetDialog.dismiss()
                        }
                    } else {
                        ivSideSheetSettings.visibility = View.GONE
                    }
                    toggleSideSheetChattingRoomNotification.isChecked = isNotificationEnabled
                    toggleSideSheetChattingRoomNotification.setOnClickListener {
                        isNotificationEnabled = !isNotificationEnabled
                        toggleSideSheetChattingRoomNotification.isChecked = isNotificationEnabled
                        chattingRoomViewModel.putChattingRoomAlarm(
                            chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1,
                            PutChattingRoomAlarmRequest(
                                isNotificationEnabled,
                            ),
                        )
                    }
                    layoutSideSheetPostInfo.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putLong(
                            "boardId",
                            chattingRoomViewModel.chattingRoomInfo.value
                                ?.board
                                ?.boardId!!,
                        )
                        findNavController().navigate(R.id.action_chattingRoomFragment_to_readPostFragment, bundle)
                        sideSheetDialog.dismiss()
                    }
                }

                sideSheetDialog.show()
            }
            imgbtnHeaderMenuBack.setOnClickListener {
                setOnBackPressedAction()
            }
            tvHeaderMenuTitle.text = info.board.title
            tvHeaderMenuMemberCount.text = info.board.currentPerson.toString()
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
                    val request =
                        JSONObject()
                            .apply {
                                put("chatRoomId", chattingRoomViewModel.chattingRoomInfo.value?.chatRoomId ?: -1)
                            }.toString()
                    chattingRoomViewModel.leaveChattingRoom(request)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun showAlertSnackbar(str: Int) {
        val snackbarView = layoutInflater.inflate(R.layout.layout_chatting_custom_snackbar, null)
        val snackbarText = snackbarView.findViewById<TextView>(R.id.tv_chatting_custom_snackbar)
        snackbarText.setText(str)
        val params =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
            )
        params.topToBottom = R.id.layout_header_chatting_room
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

        snackbarView.alpha = 0f

        val rootView = binding.root
        rootView.addView(snackbarView, params)
        snackbarView
            .animate()
            .alpha(1f)
            .setDuration(300)
            .start()

        Handler(Looper.getMainLooper()).postDelayed({
            snackbarView
                .animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction { rootView.removeView(snackbarView) }
                .start()
        }, 1000)
    }
}
