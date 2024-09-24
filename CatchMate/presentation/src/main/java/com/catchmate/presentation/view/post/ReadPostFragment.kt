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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.catchmate.domain.model.BoardReadResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentReadPostBinding
import com.catchmate.presentation.util.AgeUtils
import com.catchmate.presentation.util.DateUtils
import com.catchmate.presentation.util.GenderUtils
import com.catchmate.presentation.util.ResourceUtil
import com.catchmate.presentation.viewmodel.LocalDataViewMdoel
import com.catchmate.presentation.viewmodel.ReadPostViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadPostFragment : Fragment() {
    private var _binding: FragmentReadPostBinding? = null
    val binding get() = _binding!!

    private var boardId: Long = 0L
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
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { accessToken ->
            if (accessToken != null) {
                this.accessToken = accessToken
                readPostViewModel.getBoard(accessToken, boardId)
            }
        }
        localDataViewModel.refreshToken.observe(viewLifecycleOwner) { refreshToken ->
            if (refreshToken != null) {
                this.refreshToken = refreshToken
            }
        }
    }

    private fun initHeader() {
        binding.layoutReadPostHeader.apply {
            imgbtnHeaderKebabMenuBack.setOnClickListener {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.readPostFragment, true)
                        .build()
                findNavController().navigate(R.id.action_readPostFragment_to_homeFragment, null, navOptions)
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
                            Log.e("수정", "수정")
                            true
                        }
                        R.id.menuitem_post_delete -> {
                            Log.e("삭제", "삭제")
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
                    readPostViewModel.postBoardLike(accessToken, boardId, 1)
                } else {
                    readPostViewModel.postBoardLike(accessToken, boardId, 0)
                }
            }
        }
    }

    private fun initViewModel() {
        readPostViewModel.boardReadResponse.observe(viewLifecycleOwner) { response ->
            setPostData(response)
        }

        readPostViewModel.boardLikeResponse.observe(viewLifecycleOwner) { code ->
            if (code != null) {
                Snackbar.make(requireView(), R.string.post_read_toast_msg, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setPostData(post: BoardReadResponse) {
        binding.apply {
            tvReadPostTitle.text = post.title
            tvReadPostDate.text = DateUtils.formatPlayDate(post.gameDate)
            tvReadPostPlace.text = post.location
            tvReadPostPeopleCount.text = post.maxPerson.toString() + "명"
            val isCheerTeam = post.homeTeam == post.cheerTeam
            ResourceUtil.setTeamViewResources(post.homeTeam, isCheerTeam, ivReadPostHomeTeamBg, ivReadPostHomeTeamLogo, "read", requireContext())
            ResourceUtil.setTeamViewResources(post.awayTeam, !isCheerTeam, ivReadPostAwayTeamBg, ivReadPostAwayTeamLogo, "read", requireContext())
            tvReadPostWriterNickname.text = post.writer.nickName
            DrawableCompat
                .setTint(
                    tvReadPostWriterTeam.background,
                    ResourceUtil
                        .convertTeamColor(
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
        }
    }
}
