package com.catchmate.presentation.view.chatting

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingSettingBinding
import com.catchmate.presentation.interaction.OnKickOutClickListener
import com.catchmate.presentation.viewmodel.ChattingSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingSettingFragment :
    Fragment(),
    OnKickOutClickListener {
    private var _binding: FragmentChattingSettingBinding? = null
    val binding get() = _binding!!

    private val chattingSettingViewModel: ChattingSettingViewModel by viewModels()

    private lateinit var chattingCrewAdapter: ChattingCrewListAdapter
    private lateinit var chattingRoomImage: String
    private lateinit var chattingCrewList: MutableList<GetUserProfileResponse>
    private var loginUserId: Long = -1L
    private var writerId: Long = -1L
    private var chatRoomId: Long = -1L
    private var deletedCrewId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chattingRoomImage = getChattingRoomImage()
        chattingCrewList = getChattingCrewList() ?: mutableListOf()
        loginUserId = getLoginUserId()
        writerId = getWriterId()
        chatRoomId = getChatRoomId()
        Log.e("값 확인", "$chattingRoomImage \n $chattingCrewList \n $loginUserId \n $writerId \n $chatRoomId ")
    }

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
        initViewModel()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getChattingRoomImage(): String = arguments?.getString("chattingRoomImage") ?: ""

    private fun getChattingCrewList(): MutableList<GetUserProfileResponse>? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("chattingCrewList", GetChattingCrewListResponse::class.java)?.userInfoList?.toMutableList()
        } else {
            val parcelable = arguments?.getParcelable("chattingCrewList") as GetChattingCrewListResponse?
            parcelable?.userInfoList?.toMutableList()
        }

    private fun getLoginUserId(): Long = arguments?.getLong("loginUserId") ?: -1L

    private fun getWriterId(): Long = arguments?.getLong("writerId") ?: -1L

    private fun getChatRoomId(): Long = arguments?.getLong("chatRoomId") ?: -1L

    private fun initHeader() {
        binding.layoutHeaderChattingSetting.apply {
            tvHeaderTextTitle.setText(R.string.chatting_setting_title)
            imgbtnHeaderTextBack.setImageResource(R.drawable.vec_all_close_20dp)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initViewModel() {
        chattingSettingViewModel.kickOutChattingCrewResponse.observe(viewLifecycleOwner) { response ->
            if (response.state) {
                Log.d("강퇴 성공", "✅ $deletedCrewId \n $chattingCrewList")
                chattingCrewList = chattingCrewList.filter { it.userId != deletedCrewId }.toMutableList()
                Log.e("crew list", "$chattingCrewList")
                chattingCrewAdapter.submitList(chattingCrewList)
            }
        }
    }

    private fun initRecyclerView() {
        chattingCrewAdapter = ChattingCrewListAdapter(loginUserId, writerId, "chattingSetting", this@ChattingSettingFragment)
        binding.rvChattingParticipant.apply {
            adapter = chattingCrewAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        chattingCrewAdapter.submitList(chattingCrewList)
    }

    override fun onKickOutClicked(userId: Long) {
        deletedCrewId = userId
        chattingSettingViewModel.kickOutChattingCrew(chatRoomId, userId)
    }
}
