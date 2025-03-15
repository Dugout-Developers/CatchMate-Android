package com.catchmate.presentation.view.chatting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.domain.model.enumclass.ChatMessageType
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingRoomBinding
import com.catchmate.presentation.databinding.LayoutChattingSideSheetBinding
import com.catchmate.presentation.databinding.LayoutSimpleDialogBinding
import com.catchmate.presentation.util.DateUtils.formatISODateTime
import com.catchmate.presentation.util.ResourceUtil.setTeamViewResources
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ChattingRoomViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class ChattingRoomFragment : BaseFragment<FragmentChattingRoomBinding>(FragmentChattingRoomBinding::inflate) {
    private val chattingRoomViewModel: ChattingRoomViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()
    private val chatRoomId by lazy { arguments?.getLong("chatRoomId") ?: -1L }
    private var userId: Long = -1L
    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isApiCalled = false
    private var isFirstLoad = true
    private var isNotificationEnabled = false
    private lateinit var chatListAdapter: ChatListAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        localDataViewModel.getAccessToken()
        initViewModel()
        chattingRoomViewModel.getChattingRoomInfo(chatRoomId)
        localDataViewModel.getUserId()
        initChatBox()
        initSendBtn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chattingRoomViewModel.topic.dispose()
        chattingRoomViewModel.stompConnection.dispose()
    }

    private fun initViewModel() {
        chattingRoomViewModel.getChattingHistoryResponse.observe(viewLifecycleOwner) { response ->
            if (response.isFirst && response.isLast && response.totalElements == 0) {
                Log.d("빈 채팅방 목록", "empty")
            } else {
                Log.d("👀observer", "work \n ${response.chatMessageInfoList.size}")
                if (isApiCalled) {
                    val currentList = chatListAdapter.currentList.toMutableList()
                    Log.e("IS API CALLED", "🅾️")
                    currentList.addAll(response.chatMessageInfoList)
                    chatListAdapter.submitList(currentList)
                    isApiCalled = false
                } else {
                    chatListAdapter.submitList(response.chatMessageInfoList) {
                        // 수신 메시지 추가 후 콜백을 통해 최신 메시지로 스크롤 이동
                        binding.rvChattingRoomChatList.smoothScrollToPosition(0)
                        isApiCalled = false
                    }
                }
                isLastPage = response.isLast
                isLoading = false
            }
        }
        chattingRoomViewModel.getChattingCrewListResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                initRecyclerView(response.userInfoList)
            }
        }
        chattingRoomViewModel.chattingRoomInfo.observe(viewLifecycleOwner) { info ->
            if (info != null) {
                isNotificationEnabled = info.isNotificationEnabled
                initChatRoomInfo(info)
                initHeader(info)
            }
        }
        chattingRoomViewModel.deleteChattingRoomResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (it.state) {
                    Log.d("채팅방 나가기 성공", "나가기 성공")
                    setFragmentResult("deleteChattingRoomResultKey", bundleOf("chatRoomId" to chatRoomId))
                    findNavController().popBackStack()
                } else {
                    Log.e("채팅방 오류", "나가기 실패")
                }
            }
        }
        chattingRoomViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.chattingRoomFragment, true)
                        .build()
                findNavController().navigate(R.id.action_chattingRoomFragment_to_loginFragment, null, navOptions)
            }
        }
        chattingRoomViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
        localDataViewModel.userId.observe(viewLifecycleOwner) { id ->
            userId = id
            chattingRoomViewModel.getChattingCrewList(chatRoomId)
        }
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { token ->
            chattingRoomViewModel.connectToWebSocket(chatRoomId, token)
        }
        chattingRoomViewModel.isMessageSent.observe(viewLifecycleOwner) { isSent ->
            if (isSent) {
                binding.edtChattingRoomChatBox.setText("")
            } else {
                Snackbar.make(requireView(), "메시지 전송에 실패하였습니다. 잠시 후 다시 시도해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView(list: List<GetUserProfileResponse>) {
        Log.e("userID", userId.toString())
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
                            currentPage += 1
                            getChattingHistory()
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
        Log.e("api 호출", "호출 $isLoading $isLastPage")
        if (isLoading || isLastPage) return
        isLoading = true
        chattingRoomViewModel.getChattingHistory(chatRoomId, currentPage)
        isApiCalled = true
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
            val message =
                JSONObject()
                    .apply {
                        put("messageType", ChatMessageType.TALK.name)
                        put("content", binding.edtChattingRoomChatBox.text.toString())
                        put("senderId", userId)
                    }.toString()

            chattingRoomViewModel.sendMessage(chatRoomId, message)
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
                    var crewAdapter = ChattingCrewListAdapter(userId, info.boardInfo.userInfo.userId, "chattingRoom")
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
                            // 채팅방 이미지 url, 참여자 목록, 로그인 유저 id, 게시글 작성자 id, 채팅방 id 넘김
                            val bundle =
                                Bundle().apply {
                                    putString("chattingRoomImage", info.chatRoomImage)
                                    putParcelable("chattingCrewList", chattingRoomViewModel.getChattingCrewListResponse.value)
                                    putLong("loginUserId", userId)
                                    putLong("writerId", info.boardInfo.userInfo.userId)
                                    putLong("chatRoomId", chatRoomId)
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
                        chattingRoomViewModel.putChattingRoomAlarm(chatRoomId, isNotificationEnabled)
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
