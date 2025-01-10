package com.catchmate.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentHomeBinding
import com.catchmate.presentation.interaction.OnClubFilterSelectedListener
import com.catchmate.presentation.interaction.OnDateFilterSelectedListener
import com.catchmate.presentation.interaction.OnPostItemClickListener
import com.catchmate.presentation.viewmodel.HomeViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    Fragment(),
    OnPostItemClickListener,
    OnDateFilterSelectedListener,
    OnClubFilterSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()

    private var page: Long = 1
    private var isNextPageExist = true

    private var gameStartDate: String? = null
    private var maxPerson: Int? = null
    private var preferredTeamId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        initDateFilter()
        initTeamFilter()
        initHeadCountFilter()
        initBoardList()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTokens() {
        localDataViewModel.getUserId()
        localDataViewModel.userId.observe(viewLifecycleOwner) { userId ->
            if (userId == -1L) {
                getUserProfile()
            }
        }
    }

    private fun initHeader() {
        binding.layoutHeaderHome.apply {
            imgbtnHeaderHomeNotification.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
            }
        }
    }

    private fun initViewModel() {
        homeViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.homeFragment, true)
                        .build()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment, null, navOptions)
            }
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
    }

    private fun initBoardList() {
        homeViewModel.getBoardList()
        homeViewModel.getBoardListResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (response.boardInfoList.isNotEmpty()) {
                    binding.rvHomePosts.visibility = View.VISIBLE
                    binding.layoutHomeNoList.visibility = View.GONE
                    Log.e("게시글 목록 존재", response.boardInfoList.size.toString())
//                    isNextPageExist = true
                    val adapter = binding.rvHomePosts.adapter as HomePostAdapter
                    adapter.updatePostList(response.boardInfoList)
                } else {
                    // page 1일때 아닐때로 분기해서 게시글 목록이 아예 없는지 구분 필요
                    Log.e("게시글 목록 더이상 없음", response.boardInfoList.size.toString())
//                    isNextPageExist = false
                    binding.rvHomePosts.visibility = View.GONE
                    binding.layoutHomeNoList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initDateFilter() {
        binding.hfvHomeDateFilter.setOnClickListener {
            val dateFilterBottomSheet = HomeDateFilterBottomSheetFragment(gameStartDate)
            dateFilterBottomSheet.setOnDateFilterSelectedListener(this@HomeFragment)
            dateFilterBottomSheet.show(requireActivity().supportFragmentManager, dateFilterBottomSheet.tag)
        }
    }

    private fun initTeamFilter() {
        binding.hfvHomeTeamFilter.setOnClickListener {
            val teamFilterBottomSheet = HomeTeamFilterBottomSheetFragment(preferredTeamId)
            teamFilterBottomSheet.setOnClubSelectedListener(this@HomeFragment)
            teamFilterBottomSheet.show(requireActivity().supportFragmentManager, teamFilterBottomSheet.tag)
        }
    }

    private fun initHeadCountFilter() {
        binding.hfvHomeMemberCountFilter.setOnClickListener {
            val headCountFilterBottomSheet = HomeHeadCountFilterBottomSheetFragment()
            headCountFilterBottomSheet.show(requireActivity().supportFragmentManager, headCountFilterBottomSheet.tag)
        }
    }

    private fun initRecyclerView() {
        binding.rvHomePosts.apply {
            adapter = HomePostAdapter(requireContext(), layoutInflater, this@HomeFragment)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            addOnScrollListener(
//                object : RecyclerView.OnScrollListener() {
//                    override fun onScrolled(
//                        recyclerView: RecyclerView,
//                        dx: Int,
//                        dy: Int,
//                    ) {
//                        super.onScrolled(recyclerView, dx, dy)
//                        val lastVisibleItemPosition =
//                            (recyclerView.layoutManager as LinearLayoutManager)
//                                .findLastCompletelyVisibleItemPosition()
//                        val itemTotalCount = recyclerView.adapter!!.itemCount
//
//                        if (lastVisibleItemPosition >= itemTotalCount - 1) { // 새로운 목록 불러와야함
//                            if (isNextPageExist) {
//                                homeViewModel.getBoardPaging(
//                                    pageNum = page++,
//                                )
//                            }
//                        }
//                    }
//                },
//            )
        }
    }

    private fun getUserProfile() {
        homeViewModel.getUserProfile()
        homeViewModel.userProfile.observe(viewLifecycleOwner) { response ->
            response?.let {
                localDataViewModel.saveUserId(response.userId)
            }
        }
    }

    override fun onPostItemClicked(boardId: Long) {
        val bundle = Bundle()
        bundle.putLong("boardId", boardId)
        findNavController().navigate(R.id.action_homeFragment_to_readPostFragment, bundle)
    }

    override fun onDateSelected(date: String?) {
        gameStartDate = date
        homeViewModel.getBoardList(
            gameStartDate,
            maxPerson,
            preferredTeamId,
        )
        binding.hfvHomeDateFilter.setDateFilterText(gameStartDate)
        binding.hfvHomeDateFilter.setFilterTextColor(gameStartDate != null)
    }

    override fun onClubFilterSelected(clubId: Int?) {
        preferredTeamId = clubId
        Log.e("CLUB", clubId.toString())
        homeViewModel.getBoardList(
            gameStartDate,
            maxPerson,
            preferredTeamId,
        )
        binding.hfvHomeTeamFilter.setClubFilterText(clubId)
        binding.hfvHomeTeamFilter.setFilterTextColor(clubId != null)
    }
}
