package com.catchmate.presentation.view.chatting

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.catchmate.domain.model.chatting.GetChattingCrewListResponse
import com.catchmate.domain.model.user.GetUserProfileResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentChattingSettingBinding
import com.catchmate.presentation.interaction.OnKickOutClickListener
import com.catchmate.presentation.util.ImageUtils.convertBitmapToMultipart
import com.catchmate.presentation.viewmodel.ChattingSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChattingSettingFragment :
    Fragment(),
    OnKickOutClickListener {
    private var _binding: FragmentChattingSettingBinding? = null
    val binding get() = _binding!!

    private val chattingSettingViewModel: ChattingSettingViewModel by viewModels()

    private lateinit var requestAlbumLauncher: ActivityResultLauncher<Intent>
    private lateinit var chattingCrewAdapter: ChattingCrewListAdapter

    private lateinit var chattingRoomImage: String
    private lateinit var chattingCrewList: MutableList<GetUserProfileResponse>
    private var loginUserId: Long = -1L
    private var writerId: Long = -1L
    private var chatRoomId: Long = -1L
    private var deletedCrewId: Long = -1L
    private var updatedBitmap: Bitmap? = null

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
        createAlbumBitmap()
        initRecyclerView()
        initChattingRoomImageView()
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
        chattingSettingViewModel.patchChattingRoomImageResponse.observe(viewLifecycleOwner) { response ->
            if (response.state) {
                Log.d("📸채팅방 프로필 변경 성공", "성공")
                binding.ivChattingSettingThumbnail.setImageBitmap(updatedBitmap)
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

    private fun initChattingRoomImageView() {
        Glide
            .with(this@ChattingSettingFragment)
            .load(chattingRoomImage)
            .error(R.drawable.ic_notification_samsung_device)
            .into(binding.ivChattingSettingThumbnail)
        binding.ivChattingSettingThumbnail.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestAlbumLauncher.launch(intent)
        }
    }

    private fun createAlbumBitmap() {
        requestAlbumLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) {
                val option = BitmapFactory.Options()
                option.inSampleSize = 4
                if (it.resultCode == Activity.RESULT_OK) {
                    it.data?.data?.let { uri ->
                        val inputStream = requireActivity().contentResolver.openInputStream(uri)
                        val originalBitmap = BitmapFactory.decodeStream(inputStream, null, option)
                        inputStream?.close()

                        val resizedBitmap =
                            originalBitmap?.let { bitmap ->
                                Bitmap.createScaledBitmap(bitmap, 200, 200, true)
                            }

                        resizedBitmap?.let { bitmap ->
                            updatedBitmap = bitmap
                            val multipart =
                                convertBitmapToMultipart(
                                    requireContext(),
                                    bitmap,
                                    "chat_room_${chatRoomId}_image.jpg",
                                    "chatRoomImage",
                                )
                            chattingSettingViewModel.patchChattingRoomImage(chatRoomId, multipart)
                        }
                    }
                }
            }
    }

    override fun onKickOutClicked(userId: Long) {
        deletedCrewId = userId
        chattingSettingViewModel.kickOutChattingCrew(chatRoomId, userId)
    }
}
