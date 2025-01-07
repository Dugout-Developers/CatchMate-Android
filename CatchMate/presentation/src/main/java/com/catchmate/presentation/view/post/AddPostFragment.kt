package com.catchmate.presentation.view.post

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.board.PutBoardRequest
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentAddPostBinding
import com.catchmate.presentation.interaction.OnCheerTeamSelectedListener
import com.catchmate.presentation.interaction.OnDateTimeSelectedListener
import com.catchmate.presentation.interaction.OnPeopleCountSelectedListener
import com.catchmate.presentation.interaction.OnPlaceSelectedListener
import com.catchmate.presentation.interaction.OnTeamSelectedListener
import com.catchmate.presentation.util.AgeUtils
import com.catchmate.presentation.util.ClubUtils
import com.catchmate.presentation.util.DateUtils
import com.catchmate.presentation.util.GenderUtils
import com.catchmate.presentation.viewmodel.AddPostViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment :
    Fragment(),
    OnPeopleCountSelectedListener,
    OnDateTimeSelectedListener,
    OnTeamSelectedListener,
    OnCheerTeamSelectedListener,
    OnPlaceSelectedListener {
    private var _binding: FragmentAddPostBinding? = null
    val binding get() = _binding!!

    private val addPostViewModel: AddPostViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()

    private lateinit var accessToken: String
    private lateinit var refreshToken: String
    private var boardInfo: GetBoardResponse? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardInfo = getBoardInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddPostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        boardInfo?.let {
            isEditMode = true
            setBoardData(it)
        }

        getTokens()
        initViewModel()
        initHeader()
        initFooter()
        initAdditionalInfoEdt()
        initBottomSheets()
        initAgeChip()
        initTitleTextView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBoardData(response: GetBoardResponse) {
        binding.apply {
            edtAddPostTitle.setText(response.title)
            tvAddPostPeopleCount.text = response.maxPerson.toString()
            addPostViewModel.setGameDate(DateUtils.formatGameDateTimeEditBoard(response.gameDate))
            addPostViewModel.setHomeTeamName(response.homeTeam)
            addPostViewModel.setAwayTeamName(response.awayTeam)
            tvAddPostCheerTeam.text = response.cheerTeam
            tvAddPostPlace.text = response.location
            edtAddPostAdditionalInfo.setText(response.addInfo)
            tvAddPostAdditionalInfoLetterCount.text = response.addInfo.length.toString()
            layoutAddPostFooter.btnFooterOne.isEnabled = true
            // 성별, 나이대 반영 필
        }
    }

    private fun getBoardInfo(): GetBoardResponse? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("boardInfo", GetBoardResponse::class.java)
        } else {
            arguments?.getSerializable("boardInfo") as GetBoardResponse
        }

    private fun getTokens() {
        localDataViewModel.getAccessToken()
        localDataViewModel.getRefreshToken()
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { accessToken ->
            if (accessToken != null) {
                this.accessToken = accessToken
            }
        }
        localDataViewModel.refreshToken.observe(viewLifecycleOwner) { refreshToken ->
            if (refreshToken != null) {
                this.refreshToken = refreshToken
            }
        }
    }

    private fun initHeader() {
        binding.layoutAddPostHeader.run {
            imgbtnHeaderTextBack.setOnClickListener {
                if (isEditMode) {
                    findNavController().popBackStack()
                } else {
                    val navOptions =
                        NavOptions
                            .Builder()
                            .setPopUpTo(R.id.addPostFragment, true)
                            .build()
                    findNavController().navigate(R.id.action_addPostFragment_to_homeFragment, null, navOptions)
                }
            }
            tvHeaderTextTitle.visibility = View.GONE
            tvHeaderTextSub.visibility = View.VISIBLE
            tvHeaderTextSub.setText(R.string.temporary_storage)
        }
    }

    private fun initViewModel() {
        addPostViewModel.homeTeamName.observe(viewLifecycleOwner) { homeTeamName ->
            if (homeTeamName != null) {
                binding.tvAddPostHomeTeam.text = homeTeamName
                if (homeTeamName != "자이언츠" && homeTeamName != "이글스" && homeTeamName != "라이온즈") {
                    initPlaceTextView()
                } else {
                    binding.tvAddPostPlace.text = ""
                }
            }
        }

        addPostViewModel.awayTeamName.observe(viewLifecycleOwner) { awayTeamName ->
            if (awayTeamName != null) {
                binding.tvAddPostAwayTeam.text = awayTeamName
            }
        }

        addPostViewModel.gameDateTime.observe(viewLifecycleOwner) { gameDateTime ->
            if (gameDateTime != null) {
                binding.tvAddPostGameDateTime.text = DateUtils.formatPlayDate(gameDateTime)
            }
        }
    }

    private fun initFooter() {
        binding.layoutAddPostFooter.btnFooterOne.setText(R.string.post_complete)
        binding.layoutAddPostFooter.btnFooterOne.setOnClickListener {
            val title = binding.edtAddPostTitle.text.toString()
            val content = binding.edtAddPostAdditionalInfo.text.toString()
            val maxPerson =
                binding.tvAddPostPeopleCount.text
                    .toString()
                    .toInt()
            val cheerClubId = ClubUtils.convertClubNameToId(binding.tvAddPostCheerTeam.text.toString())
            val preferredGender =
                if (binding.chipgroupAddPostGender.checkedChipId != View.NO_ID) {
                    GenderUtils.convertPostGender(
                        binding.root
                            .findViewById<Chip>(
                                binding.chipgroupAddPostGender.checkedChipId,
                            ).text
                            .toString(),
                    )
                } else {
                    ""
                }
            val preferredAgeRange =
                if (binding.chipgroupAddPostAge.checkedChipIds.isNotEmpty()) {
                    getCheckedAgeRange(binding.chipgroupAddPostAge.checkedChipIds).toList()
                } else {
                    emptyList()
                }
            val homeClubId = ClubUtils.convertClubNameToId(addPostViewModel.homeTeamName.value.toString())
            val awayClubId = ClubUtils.convertClubNameToId(addPostViewModel.awayTeamName.value.toString())
            val gameStartDate = addPostViewModel.gameDateTime.value.toString()
            val location = binding.tvAddPostPlace.text.toString()
            val gameRequest = GameInfo(homeClubId, awayClubId, gameStartDate, location)

            if (isEditMode) {
                val boardEditRequest =
                    PutBoardRequest(
                        boardInfo?.boardId ?: 0,
                        title,
                        gameStartDate,
                        location,
                        "",
                        "",
                        "",
                        boardInfo?.currentPerson ?: 0,
                        boardInfo?.maxPerson ?: 0,
                        "",
                        null,
                        content,
                    )
                putBoard(boardEditRequest)
            } else {
                val boardWriteRequest =
                    PostBoardRequest(
                        title,
                        content,
                        maxPerson,
                        cheerClubId,
                        preferredGender,
                        preferredAgeRange,
                        gameRequest,
                        true,
                    )
                postBoardWrite(boardWriteRequest)
            }
        }
    }

    private fun postBoardWrite(boardWriteRequest: PostBoardRequest) {
        addPostViewModel.postBoard(
            boardWriteRequest,
        )
        addPostViewModel.postBoardResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.e("boardWriteResponse", response.boardId.toString())
                val bundle = Bundle()
                bundle.putLong("boardId", response.boardId)
                findNavController().navigate(R.id.action_addPostFragment_to_readPostFragment, bundle)
            }
        }
    }

    private fun putBoard(putBoardRequest: PutBoardRequest) {
        addPostViewModel.putBoard(putBoardRequest)
        addPostViewModel.putBoardResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("boardEditResponse", response.boardId.toString())
                val bundle = Bundle()
                bundle.putLong("boardId", response.boardId)
                findNavController().navigate(R.id.action_addPostFragment_to_readPostFragment, bundle)
            }
        }
    }

    private fun initTitleTextView() {
        binding.edtAddPostTitle.doAfterTextChanged {
            checkInputFieldsAreEmpty()
        }
    }

    private fun initAgeChip() {
        binding.chipgroupAddPostAge.setOnCheckedStateChangeListener { group, checkedIds ->
            if (!checkedIds.contains(R.id.chip_add_post_age_regardless) && checkedIds.size == 5) {
                group.clearCheck()
                binding.chipAddPostAgeRegardless.isChecked = true
            }
            if (checkedIds.contains(R.id.chip_add_post_age_regardless) && checkedIds.size > 1) {
                binding.chipAddPostAgeRegardless.isChecked = false
            }
        }
    }

    private fun initAdditionalInfoEdt() {
        binding.edtAddPostAdditionalInfo.apply {
            setOnTouchListener { v, event ->
                if (v.id == R.id.edt_add_post_additional_info) {
                    v.parent.requestDisallowInterceptTouchEvent(true)
                    if (event.action == android.view.MotionEvent.ACTION_UP) {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                v.onTouchEvent(event)
                true
            }

            doOnTextChanged { text, _, _, _ ->
                val currentL = text?.length ?: 0
                binding.tvAddPostAdditionalInfoLetterCount.text = currentL.toString()
            }

            doAfterTextChanged {
                checkInputFieldsAreEmpty()
            }
        }
    }

    private fun initBottomSheets() {
        binding.apply {
            tvAddPostPeopleCount.setOnClickListener {
                val peopleCountBottomSheet = PostHeadCountBottomSheetFragment()
                peopleCountBottomSheet.setOnPeopleCountSelectedListener(this@AddPostFragment)
                peopleCountBottomSheet.show(requireActivity().supportFragmentManager, peopleCountBottomSheet.tag)
            }
            tvAddPostGameDateTime.setOnClickListener {
                val dateTimeBottomSheet = PostDateTimeBottomSheetFragment()
                dateTimeBottomSheet.setOnDateTimeSelectedListener(this@AddPostFragment)
                dateTimeBottomSheet.show(requireActivity().supportFragmentManager, dateTimeBottomSheet.tag)
            }
            tvAddPostHomeTeam.setOnClickListener {
                val playTeamBottomSheet =
                    PostPlayTeamBottomSheetFragment(
                        addPostViewModel.homeTeamName.value,
                        addPostViewModel.awayTeamName.value,
                    )
                playTeamBottomSheet.setOnTeamSelectedListener(this@AddPostFragment, "home")
                playTeamBottomSheet.show(requireActivity().supportFragmentManager, playTeamBottomSheet.tag)
            }
            tvAddPostAwayTeam.setOnClickListener {
                val playTeamBottomSheet =
                    PostPlayTeamBottomSheetFragment(
                        addPostViewModel.awayTeamName.value,
                        addPostViewModel.homeTeamName.value,
                    )
                playTeamBottomSheet.setOnTeamSelectedListener(this@AddPostFragment, "away")
                playTeamBottomSheet.show(requireActivity().supportFragmentManager, playTeamBottomSheet.tag)
            }
            tvAddPostCheerTeam.setOnClickListener {
                if (addPostViewModel.homeTeamName.value == null || addPostViewModel.awayTeamName.value == null) {
                    return@setOnClickListener
                }
                val cheerTeamBottomSheet =
                    PostCheerTeamBottomSheetFragment(
                        addPostViewModel.homeTeamName.value!!,
                        addPostViewModel.awayTeamName.value!!,
                    )
                cheerTeamBottomSheet.setOnCheerTeamSelectedListener(this@AddPostFragment)
                cheerTeamBottomSheet.show(requireActivity().supportFragmentManager, cheerTeamBottomSheet.tag)
            }
            tvAddPostPlace.setOnClickListener {
                if (addPostViewModel.homeTeamName.value !=
                    getString(
                        R.string.team_lotte_giants,
                    ) &&
                    addPostViewModel.homeTeamName.value !=
                    getString(
                        R.string.team_hanwha_eagles,
                    ) &&
                    addPostViewModel.homeTeamName.value !=
                    getString(
                        R.string.team_samsung_lions,
                    )
                ) {
                    initPlaceTextView()
                    return@setOnClickListener
                }
                val placeBottomSheet = PostPlaceBottomSheetFragment(addPostViewModel.homeTeamName.value!!)
                placeBottomSheet.setOnPlaceSelectedListener(this@AddPostFragment)
                placeBottomSheet.show(requireActivity().supportFragmentManager, placeBottomSheet.tag)
            }
        }
    }

    private fun initPlaceTextView() {
        binding.tvAddPostPlace.text =
            when (addPostViewModel.homeTeamName.value) {
                getString(R.string.team_nc_dinos) -> getString(R.string.post_place_nc)
                getString(R.string.team_ssg_landers) -> getString(R.string.post_place_ssg)
                getString(R.string.team_doosan_bears) -> getString(R.string.post_place_doosan_lg)
                getString(R.string.team_kt_wiz) -> getString(R.string.post_place_kt)
                getString(R.string.team_kia_tigers) -> getString(R.string.post_place_kia)
                getString(R.string.team_lg_twins) -> getString(R.string.post_place_doosan_lg)
                else -> getString(R.string.post_place_kiwoom)
            }
    }

    private fun checkInputFieldsAreEmpty() {
        val title = binding.edtAddPostTitle.text.toString()
        val peopleCount = binding.tvAddPostPeopleCount.text.toString()
        val dateTime = addPostViewModel.gameDateTime.value.toString()
        val homeTeam = addPostViewModel.homeTeamName.value.toString()
        val awayTeam = addPostViewModel.awayTeamName.value.toString()
        val cheerTeam = binding.tvAddPostCheerTeam.text.toString()
        val place = binding.tvAddPostPlace.text.toString()
        val additionalInfo = binding.edtAddPostAdditionalInfo.text.toString()
        // 연령대 추후 로직 변경 시 적용

        binding.layoutAddPostFooter.btnFooterOne.isEnabled =
            title.isNotEmpty() &&
            peopleCount.isNotEmpty() &&
            dateTime.isNotEmpty() &&
            homeTeam.isNotEmpty() &&
            awayTeam.isNotEmpty() &&
            cheerTeam.isNotEmpty() &&
            place.isNotEmpty() &&
            additionalInfo.isNotEmpty()
    }

    private fun getCheckedAgeRange(checkedChipIds: List<Int>): MutableList<String> {
        val ages: MutableList<String> = mutableListOf()
        checkedChipIds.forEach { id ->
            val age = AgeUtils.convertPostAge(
                binding.root
                    .findViewById<Chip>(id).text
                    .toString(),
            )
            ages.add(age)
        }
        return ages
    }

    override fun onPeopleCountSelected(count: Int) {
        binding.tvAddPostPeopleCount.text = count.toString()
        checkInputFieldsAreEmpty()
    }

    override fun onDateTimeSelected(
        date: String,
        time: String,
    ) {
        addPostViewModel.setGameDate(DateUtils.formatGameDateTime(date, time))
        checkInputFieldsAreEmpty()
    }

    override fun onTeamSelected(
        teamName: String,
        teamType: String,
    ) {
        if (teamType == "home") {
            addPostViewModel.setHomeTeamName(teamName)
        } else {
            addPostViewModel.setAwayTeamName(teamName)
        }
        checkInputFieldsAreEmpty()
    }

    override fun onCheerTeamSelected(cheerTeamName: String) {
        binding.tvAddPostCheerTeam.text = cheerTeamName
        checkInputFieldsAreEmpty()
    }

    override fun onPlaceSelected(place: String) {
        binding.tvAddPostPlace.text = place
        checkInputFieldsAreEmpty()
    }
}
