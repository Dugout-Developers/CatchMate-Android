package com.catchmate.presentation.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBoardId(): Long {
        return arguments?.getLong("boardId")!!
    }

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
    private fun initViewModel() {
        readPostViewModel.boardReadResponse.observe(viewLifecycleOwner) { response ->
            setPostData(response)
        }
    }

    private fun setPostData(post: BoardReadResponse) {
        binding.apply {
            tvReadPostTitle.text = post.title
            tvReadPostDate.text = DateUtils.formatPlayDate(post.gameDate)
            tvReadPostPlace.text = post.location
            tvReadPostPeopleCount.text = post.maxPerson.toString() + "명"
            initTeamInfoViews(post.homeTeam, post.homeTeam == post.cheerTeam, ivReadPostHomeTeamBg, ivReadPostHomeTeamLogo)
            initTeamInfoViews(post.awayTeam, post.awayTeam == post.cheerTeam, ivReadPostAwayTeamBg, ivReadPostAwayTeamLogo)
            tvReadPostWriterNickname.text = post.writer.nickName
            DrawableCompat.setTint(tvReadPostWriterTeam.background, ResourceUtil.convertTeamColor(requireContext(), post.writer.favGudan, true))
            tvReadPostWriterTeam.text = post.writer.favGudan
            tvReadPostWriterCheerStyle.text = post.writer.watchStyle
            tvReadPostWriterGender.text = GenderUtils.convertBoardGender(requireContext(), post.writer.gender)
            tvReadPostWriterAge.text = AgeUtils.convertBirthDateToAge(post.writer.birthDate)
            tvReadPostAdditionalInfo.text = post.addInfo
            Glide.with(this@ReadPostFragment)
                .load(post.writer.picture)
                .into(ivReadPostWriterProfile)

            // 선호 나이대, 성별 정보 추후 서버 로직 정리되면 반영
        }
    }

    private fun initTeamInfoViews(
        teamName: String,
        isCheerTeam: Boolean,
        backgroundView: ImageView,
        logoView: ImageView,
    ) {
        // 로고 설정
        logoView.setImageResource(ResourceUtil.convertTeamLogo(teamName))
        ResourceUtil.setTeamLogoOpacity(logoView, isCheerTeam)

        // 배경색 설정
        DrawableCompat.setTint(backgroundView.background, ResourceUtil.convertTeamColor(requireContext(), teamName, isCheerTeam))
    }
}
