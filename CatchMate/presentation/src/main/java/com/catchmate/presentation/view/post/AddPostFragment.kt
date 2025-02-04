package com.catchmate.presentation.view.post

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.board.GetBoardResponse
import com.catchmate.domain.model.board.PatchBoardRequest
import com.catchmate.domain.model.board.PostBoardRequest
import com.catchmate.domain.model.enroll.GameInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentAddPostBinding
import com.catchmate.presentation.databinding.LayoutSimpleDialogBinding
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
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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
    private var isEditMode = false

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
        addPostViewModel.setBoardInfo(getBoardInfo())
        addPostViewModel.boardInfo.observe(viewLifecycleOwner) { info ->
            info?.let {
                isEditMode = true
                setBoardData(it)
            }
            initHeader()
            if (!isEditMode) {
                addPostViewModel.getTempBoard()
                addPostViewModel.getTempBoardResponse.observe(viewLifecycleOwner) { response ->
                    showImportTempBoardDialog()
                }
                addPostViewModel.noTempBoardMessage.observe(viewLifecycleOwner) { message ->
                    if (!message.isNullOrEmpty()) {
                        Log.d("NO TEMP BOARD", message)
                    }
                }
            }
        }
        initViewModel()
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
            tvAddPostPeopleCount.text = if (response.maxPerson != 0) response.maxPerson.toString() else ""
            response.gameInfo.gameStartDate?.let {
                addPostViewModel.setGameDate(DateUtils.formatGameDateTimeEditBoard(it))
            }
            if (response.gameInfo.homeClubId != 0) {
                addPostViewModel.setHomeTeamName(ClubUtils.convertClubIdToName(response.gameInfo.homeClubId))
            }
            if (response.gameInfo.awayClubId != 0) {
                addPostViewModel.setAwayTeamName(ClubUtils.convertClubIdToName(response.gameInfo.awayClubId))
            }
            if (response.cheerClubId != 0) {
                tvAddPostCheerTeam.text = ClubUtils.convertClubIdToName(response.cheerClubId)
            }
            tvAddPostPlace.text = response.gameInfo.location
            edtAddPostAdditionalInfo.setText(response.content)
            tvAddPostAdditionalInfoLetterCount.text = response.content.length.toString()
            layoutAddPostFooter.btnFooterOne.isEnabled = true

            when (response.preferredGender) {
                "F" -> chipAddPostGenderFemale.isChecked = true
                "M" -> chipAddPostGenderMale.isChecked = true
                "N" -> chipAddPostGenderRegardless.isChecked = true
            }

            val ages = AgeUtils.convertAgeStringToList(response.preferredAgeRange)
            ages.forEach { age ->
                when (age) {
                    "0" -> chipAddPostAgeRegardless.isChecked = true
                    "10" -> chipAddPostAgeTeenager.isChecked = true
                    "20" -> chipAddPostAgeTwenties.isChecked = true
                    "30" -> chipAddPostAgeThirties.isChecked = true
                    "40" -> chipAddPostAgeFourties.isChecked = true
                    "50" -> chipAddPostAgeFifties.isChecked = true
                }
            }
        }
    }

    private fun getBoardInfo(): GetBoardResponse? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("boardInfo", GetBoardResponse::class.java)
        } else {
            arguments?.getParcelable("boardInfo") as GetBoardResponse?
        }

    private fun initHeader() {
        binding.layoutAddPostHeader.run {
            tvHeaderTextTitle.visibility = View.GONE
            if (isEditMode) {
                tvHeaderTextSub.visibility = View.GONE
                imgbtnHeaderTextBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            } else {
                tvHeaderTextSub.visibility = View.VISIBLE
                tvHeaderTextSub.setText(R.string.temporary_storage)
                // 임시저장 버튼 클릭
                tvHeaderTextSub.setOnClickListener {
                    saveTempBoard()
                }
                imgbtnHeaderTextBack.setOnClickListener {
                    showHandleWritingBoardDialog()
                }
            }
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

        addPostViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                if (isTrue) {
                    val navOptions =
                        NavOptions
                            .Builder()
                            .setPopUpTo(R.id.addPostFragment, true)
                            .build()
                    findNavController().navigate(R.id.action_addPostFragment_to_loginFragment, null, navOptions)
                }
            }
        }

        addPostViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Log.e("ADD POST ERR", message.toString())
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
                    PatchBoardRequest(
                        title,
                        content,
                        maxPerson,
                        cheerClubId,
                        preferredGender,
                        preferredAgeRange,
                        gameRequest,
                        true,
                    )
                patchBoard(addPostViewModel.boardInfo.value?.boardId!!, boardEditRequest)
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

    private fun saveTempBoard() {
        binding.apply {
            val title = edtAddPostTitle.text.toString()
            val content = edtAddPostAdditionalInfo.text.toString()
            val maxPerson = if (tvAddPostPeopleCount.text.isNullOrEmpty()) 0 else tvAddPostPeopleCount.text.toString().toInt()
            val cheerClubId =
                if (tvAddPostCheerTeam.text.isNullOrEmpty()) {
                    0
                } else {
                    ClubUtils.convertClubNameToId(tvAddPostCheerTeam.text.toString())
                }
            val preferredGender =
                if (chipgroupAddPostGender.checkedChipId != View.NO_ID) {
                    GenderUtils.convertPostGender(
                        root
                            .findViewById<Chip>(
                                chipgroupAddPostGender.checkedChipId,
                            ).text
                            .toString(),
                    )
                } else {
                    ""
                }
            val preferredAgeRange =
                if (chipgroupAddPostAge.checkedChipIds.isNotEmpty()) {
                    getCheckedAgeRange(chipgroupAddPostAge.checkedChipIds).toList()
                } else {
                    emptyList()
                }
            val homeClubId =
                if (addPostViewModel.homeTeamName.value.isNullOrEmpty()) {
                    0
                } else {
                    ClubUtils.convertClubNameToId(addPostViewModel.homeTeamName.value.toString())
                }
            val awayClubId =
                if (addPostViewModel.awayTeamName.value.isNullOrEmpty()) {
                    0
                } else {
                    ClubUtils.convertClubNameToId(addPostViewModel.awayTeamName.value.toString())
                }
            val gameStartDate =
                if (addPostViewModel.gameDateTime.value.isNullOrEmpty()) {
                    null
                } else {
                    addPostViewModel.gameDateTime.value.toString()
                }
            val location = tvAddPostPlace.text.toString()
            val gameRequest = GameInfo(homeClubId, awayClubId, gameStartDate, location)
            val tempBoard =
                PostBoardRequest(
                    title,
                    content,
                    maxPerson,
                    cheerClubId,
                    preferredGender,
                    preferredAgeRange,
                    gameRequest,
                    false,
                )
            postBoardWrite(tempBoard)
        }
    }

    private fun postBoardWrite(boardWriteRequest: PostBoardRequest) {
        addPostViewModel.postBoard(
            boardWriteRequest,
        )
        addPostViewModel.postBoardResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.e("boardWriteResponse", response.boardId.toString())
                if (boardWriteRequest.isCompleted) { // 게시글 등록일 때
                    val bundle = Bundle()
                    bundle.putLong("boardId", response.boardId)
                    val navOptions =
                        NavOptions
                            .Builder()
                            .setPopUpTo(R.id.addPostFragment, true)
                            .build()
                    findNavController().navigate(R.id.action_addPostFragment_to_readPostFragment, bundle, navOptions)
                } else { // 임시 저장일 때
                    Snackbar.make(requireView(), R.string.temporary_storage_sucess_toast_msg, Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun patchBoard(
        boardId: Long,
        patchBoardRequest: PatchBoardRequest,
    ) {
        addPostViewModel.patchBoard(boardId, patchBoardRequest)
        addPostViewModel.patchBoardResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("boardEditResponse", response.boardId.toString())
                val bundle = Bundle()
                bundle.putLong("boardId", response.boardId)
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.addPostFragment, true)
                        .build()
                findNavController().navigate(R.id.action_addPostFragment_to_readPostFragment, bundle, navOptions)
            }
        }
    }

    private fun initTitleTextView() {
        binding.edtAddPostTitle.doAfterTextChanged {
            checkInputFieldsAreEmpty()
        }
    }

    private fun initAgeChip() {
        binding.apply {
            chipgroupAddPostAge.setOnCheckedStateChangeListener { group, checkedIds ->
                val ageChipIds =
                    listOf(
                        chipAddPostAgeTeenager.id,
                        chipAddPostAgeTwenties.id,
                        chipAddPostAgeThirties.id,
                        chipAddPostAgeFourties.id,
                        chipAddPostAgeFifties.id,
                    )
                val ageChips =
                    listOf(
                        chipAddPostAgeTeenager,
                        chipAddPostAgeTwenties,
                        chipAddPostAgeThirties,
                        chipAddPostAgeFourties,
                        chipAddPostAgeFifties,
                    )

                if (checkedIds.containsAll(ageChipIds)) {
                    ageChips.forEach { chip ->
                        chip.isChecked = false
                    }
                    chipAddPostAgeRegardless.isChecked = true
                }
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
            val age =
                AgeUtils.convertPostAge(
                    binding.root
                        .findViewById<Chip>(id)
                        .text
                        .toString(),
                )
            ages.add(age)
        }
        return ages
    }

    private fun showImportTempBoardDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutSimpleDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        dialogBinding.apply {
            tvSimpleDialogTitle.setText(R.string.temporary_storage_import_question)

            tvSimpleDialogNegative.apply {
                setText(R.string.temporary_storage_import_negative)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            tvSimpleDialogPositive.apply {
                setText(R.string.temporary_storage_import_positive)
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.brand500),
                )
                setOnClickListener {
                    val tempBoard = addPostViewModel.getTempBoardResponse.value!!
                    val board =
                        GetBoardResponse(
                            tempBoard.boardId,
                            tempBoard.title,
                            tempBoard.content,
                            tempBoard.cheerClubId,
                            tempBoard.currentPerson,
                            tempBoard.maxPerson,
                            tempBoard.preferredGender,
                            tempBoard.preferredAgeRange,
                            tempBoard.liftUpDate,
                            tempBoard.gameInfo,
                            tempBoard.userInfo,
                            "",
                            false,
                        )
                    addPostViewModel.setBoardInfo(board)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun showHandleWritingBoardDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = LayoutSimpleDialogBinding.inflate(layoutInflater)

        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        dialogBinding.apply {
            tvSimpleDialogTitle.setText(R.string.temporary_storage_save_question)

            tvSimpleDialogNegative.apply {
                setText(R.string.temporary_storage_save_negative)
                setOnClickListener {
                    findNavController().popBackStack()
                    dialog.dismiss()
                }
            }
            tvSimpleDialogPositive.apply {
                setText(R.string.temporary_storage_save_positive)
                setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.brand500),
                )
                setOnClickListener {
                    saveTempBoard()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
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
