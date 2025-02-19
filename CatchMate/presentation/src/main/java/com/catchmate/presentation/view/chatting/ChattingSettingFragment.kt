package com.catchmate.presentation.view.chatting

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingSettingBinding

class ChattingSettingFragment : Fragment() {
    private var _binding: FragmentChattingSettingBinding? = null
    val binding get() = _binding!!

    private lateinit var chattingRoomImage: String
    private lateinit var chattingCrewList: List<GetUserProfileResponse>
    private var loginUserId: Long = -1L
    private var writerId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chattingRoomImage = getChattingRoomImage()
        chattingCrewList = getChattingCrewList()
        loginUserId = getLoginUserId()
        writerId = getWriterId()
        Log.e("값 확인", "$chattingRoomImage \n $chattingCrewList \n $loginUserId \n $writerId ")
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getChattingRoomImage(): String = arguments?.getString("chattingRoomImage") ?: ""

    private fun getChattingCrewList(): List<GetUserProfileResponse> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("chattingCrewList", GetChattingCrewListResponse::class.java)?.userInfoList ?: emptyList()
        } else {
            val parcelable = arguments?.getParcelable("chattingCrewList") as GetChattingCrewListResponse?
            parcelable?.userInfoList ?: emptyList()
        }

    private fun getLoginUserId(): Long = arguments?.getLong("loginUserId") ?: -1L

    private fun getWriterId(): Long = arguments?.getLong("writerId") ?: -1L

    private fun initHeader() {
        binding.layoutHeaderChattingSetting.apply {
            tvHeaderTextTitle.setText(R.string.chatting_setting_title)
            imgbtnHeaderTextBack.setImageResource(R.drawable.vec_all_close_20dp)
            imgbtnHeaderTextBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
