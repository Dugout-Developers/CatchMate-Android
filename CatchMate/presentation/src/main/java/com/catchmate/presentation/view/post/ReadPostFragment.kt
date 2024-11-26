package com.catchmate.presentation.view.post

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.catchmate.domain.model.DeleteBoardRequest
import com.catchmate.domain.model.GetBoardResponse
import com.catchmate.domain.model.EnrollRequest
import com.catchmate.domain.model.EnrollState
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentReadPostBinding
import com.catchmate.presentation.databinding.LayoutApplicationDetailDialogBinding
import com.catchmate.presentation.databinding.LayoutSimpleDialogBinding
import com.catchmate.presentation.util.AgeUtils
import com.catchmate.presentation.util.DateUtils
import com.catchmate.presentation.util.GenderUtils
import com.catchmate.presentation.util.ResourceUtil.convertTeamColor
import com.catchmate.presentation.util.ResourceUtil.setTeamViewResources
import com.catchmate.presentation.viewmodel.LocalDataViewMdoel
import com.catchmate.presentation.viewmodel.ReadPostViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadPostFragment : Fragment() {
    private var _binding: FragmentReadPostBinding? = null
    val binding get() = _binding!!

    private var boardId: Long = 0L
    private var userId: Long = -1L
    private val readPostViewModel: ReadPostViewModel by viewModels()
    private val localDataViewModel: LocalDataViewMdoel by viewModels()

    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardId = getBoardId()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentReadPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        getTokens()
        initViewModel()
        initHeader()
        initFooter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBoardId(): Long = arguments?.getLong("boardId")!!

    private fun getTokens() {
        localDataViewModel.getAccessToken()
        localDataViewModel.getRefreshToken()
        localDataViewModel.getUserId()
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { accessToken ->
            if (accessToken != null) {
                this.accessToken = accessToken
                readPostViewModel.getBoard(boardId)
            }
        }
        localDataViewModel.refreshToken.observe(viewLifecycleOwner) { refreshToken ->
            if (refreshToken != null) {
                this.refreshToken = refreshToken
            }
        }
        localDataViewModel.userId.observe(viewLifecycleOwner) { userId ->
            if (userId != null) {
                this.userId = userId
            }
        }
    }

    private fun initHeader() {
        binding.layoutReadPostHeader.apply {
            imgbtnHeaderKebabMenuBack.setOnClickListener {
                findNavController().popBackStack()
            }
            imgbtnHeaderKebabMenu.setOnClickListener {
                val popup = PopupMenu(requireContext(), imgbtnHeaderKebabMenu, Gravity.CENTER, 0, R.style.CustomPopupMenu)
                popup.menuInflater.inflate(R.menu.menu_read_post_writer, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menuitem_post_up -> {
                            Log.e("끌올", "끌올")
                            true
                        }
                        R.id.menuitem_post_update -> {
                            val bundle = Bundle()
                            bundle.putSerializable("boardInfo", readPostViewModel.getBoardResponse.value)
                            findNavController().navigate(R.id.action_readPostFragment_to_addPostFragment, bundle)
                            true
                        }
                        R.id.menuitem_post_delete -> {
                            showBoardDeleteDialog()
                            true
                        }
                        else -> false
                    }
                }
                val deletePostItem = popup.menu.findItem(R.id.menuitem_post_delete)
                val s = SpannableString(deletePostItem.title)
                s.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.system_red)), 0, s.length, 0)
                deletePostItem.title = s

                popup.show()
            }
        }
    }

    private fun initFooter() {
        binding.layoutReadPostFooter.apply {
            cvLikedFooter.setOnClickListener {
                toggleLikedFooterLiked.isChecked = !toggleLikedFooterLiked.isChecked
            }
            toggleLikedFooterLiked.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    readPostViewModel.run { postBoardLike(boardId, 1) }
                } else {
                    readPostViewModel.postBoardLike(boardId, 0)
                }
            }
            btnLikedFooterRegister.setOnClickListener {
                when (readPostViewModel.boardEnrollState.value) {
                    EnrollState.APPLICABLE -> {
                        showEnrollDialog()
                    }
                    EnrollState.ACCEPTED -> {}
                    EnrollState.REJECTED -> {}
                    EnrollState.PENDING -> {}
                    null -> {}
                }
            }
        }
    }

    private fun initViewModel() {
        readPostViewModel.getBoardResponse.observe(viewLifecycleOwner) { response ->
            setPostData(response)
            initKebabMenuVisibility(response.writer.userId)
        }

        readPostViewModel.boardLikeResponse.observe(viewLifecycleOwner) { code ->
            if (code != null) {
                Snackbar.make(requireView(), R.string.post_read_toast_msg, Snackbar.LENGTH_SHORT).show()
            }
        }
        readPostViewModel.boardEnrollState.observe(viewLifecycleOwner) { state ->
            when (state!!) {
                EnrollState.APPLICABLE -> {
                    binding.layoutReadPostFooter.btnLikedFooterRegister.setText(R.string.post_register)
                }
                EnrollState.ACCEPTED -> {
                    binding.layoutReadPostFooter.btnLikedFooterRegister.setText(R.string.post_writer)
                }
                EnrollState.REJECTED -> {
                    // 구현
                }
                EnrollState.PENDING -> {
                    // 구현
                }
            }
        }
        readPostViewModel.enrollResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("직관 신청 성공", "${response.enrollId} / ${response.requestAt}")
            }
        }
        readPostViewModel.deleteBoardResponse.observe(viewLifecycleOwner) { code ->
            if (code == 200) {
                Log.d("삭제 성공", "")
                findNavController().popBackStack()
            }
        }
    }

    private fun initKebabMenuVisibility(writerUserId: Long) {
        binding.layoutReadPostHeader.imgbtnHeaderKebabMenu.visibility = if (writerUserId == userId) View.VISIBLE else View.INVISIBLE
    }

    private fun setPostData(post: GetBoardResponse) {
        binding.apply {
            tvReadPostTitle.text = post.title
            tvReadPostDate.text = DateUtils.formatPlayDate(post.gameDate)
            tvReadPostPlace.text = post.location
            tvReadPostPeopleCount.text = post.maxPerson.toString() + "명"
            val isCheerTeam = post.homeTeam == post.cheerTeam
            setTeamViewResources(
                post.homeTeam,
                isCheerTeam,
                ivReadPostHomeTeamBg,
                ivReadPostHomeTeamLogo,
                "read",
                requireContext(),
            )
            setTeamViewResources(
                post.awayTeam,
                !isCheerTeam,
                ivReadPostAwayTeamBg,
                ivReadPostAwayTeamLogo,
                "read",
                requireContext(),
            )
            tvReadPostWriterNickname.text = post.writer.nickName
            DrawableCompat
                .setTint(
                    tvReadPostWriterTeam.background,
                    convertTeamColor(
                        requireContext(),
                        post.writer.favGudan,
                        true,
                        "read",
                    ),
                )
            tvReadPostWriterTeam.text = post.writer.favGudan
            tvReadPostWriterCheerStyle.text = post.writer.watchStyle
            tvReadPostWriterGender.text = GenderUtils.convertBoardGender(requireContext(), post.writer.gender)
            tvReadPostWriterAge.text = AgeUtils.convertBirthDateToAge(post.writer.birthDate)
            tvReadPostAdditionalInfo.text = post.addInfo
            Glide
                .with(this@ReadPostFragment)
                .load(post.writer.picture)
                .into(ivReadPostWriterProfile)

            // 선호 나이대, 성별 정보 추후 서버 로직 정리되면 반영

            if (userId == post.writer.userId) {
                // 신청 수락된 경우 (참여중일때)
                readPostViewModel.setBoardEnrollState(EnrollState.ACCEPTED)
            } else {
                // 아직 신청 안한 경우
                readPostViewModel.setBoardEnrollState(EnrollState.APPLICABLE)
                // 수락됐거나 거절됐거나 신청후대기중일때 분기 필요
            }
        }
    }

    private fun showEnrollDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutApplicationDetailDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        dialogBinding.apply {
            val post = readPostViewModel.getBoardResponse.value!!

            val dateTimePair = DateUtils.formatISODateTimeToDateTime(post.gameDate)
            tvApplicationDetailDialogDate.text = dateTimePair.first
            tvApplicationDetailDialogTime.text = dateTimePair.second
            tvApplicationDetailDialogPlace.text = post.location

            val isCheerTeam = post.homeTeam == post.cheerTeam
            setTeamViewResources(
                post.homeTeam,
                isCheerTeam,
                ivApplicationDetailDialogHomeTeamBg,
                ivApplicationDetailDialogHomeTeamLogo,
                "readPost",
                requireContext(),
            )
            setTeamViewResources(
                post.awayTeam,
                !isCheerTeam,
                ivApplicationDetailDialogAwayTeamBg,
                ivApplicationDetailDialogAwayTeamLogo,
                "readPost",
                requireContext(),
            )
            tvApplicationDetailDialogTitle.setText(R.string.application_detail_dialog_write_title)

            tvApplicationDetailDialogCancel.apply {
                setText(R.string.dialog_button_cancel)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            tvApplicationDetailDialogSubmit.apply {
                setText(R.string.dialog_button_enroll)
                setOnClickListener {
                    readPostViewModel.postEnroll(
                        boardId,
                        EnrollRequest(edtApplicationDetailDialogExplain.text.toString()),
                    )
                    dialog.dismiss()
                }
            }

            edtApplicationDetailDialogExplain.doAfterTextChanged {
                if (edtApplicationDetailDialogExplain.text.toString().isNotEmpty()) {
                    tvApplicationDetailDialogSubmit.isClickable = true
                    tvApplicationDetailDialogSubmit.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.brand500),
                    )
                } else {
                    tvApplicationDetailDialogSubmit.isClickable = false
                    tvApplicationDetailDialogSubmit.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.grey500),
                    )
                }
            }
        }

        dialog.show()
    }

    private fun showBoardDeleteDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutSimpleDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        dialogBinding.apply {
            tvSimpleDialogTitle.setText(R.string.post_read_writer_menu_delete_dialog_title)

            tvSimpleDialogNegative.apply {
                setText(R.string.dialog_button_cancel)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            tvSimpleDialogPositive.apply {
                setText(R.string.dialog_button_delete)
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.brand500),
                )
                setOnClickListener {
                    readPostViewModel.deleteBoard(
                        DeleteBoardRequest(boardId),
                    )
                }
            }
        }

        dialog.show()
    }
}
