package com.catchmate.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.board.Board
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentHomeBinding
import com.catchmate.presentation.interaction.OnClubFilterSelectedListener
import com.catchmate.presentation.interaction.OnDateFilterSelectedListener
import com.catchmate.presentation.interaction.OnPersonFilterSelectedListener
import com.catchmate.presentation.interaction.OnPostItemClickListener
import com.catchmate.presentation.viewmodel.HomeViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    Fragment(),
    OnPostItemClickListener,
    OnDateFilterSelectedListener,
    OnClubFilterSelectedListener,
    OnPersonFilterSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val localDataViewModel: LocalDataViewModel by viewModels()

    private var currentPage: Int = 0
    private var isLastPage = false
    private var isLoading = false
    private var isFirstLoad = true
    private var postList: MutableList<Board> = mutableListOf()

    private var gameStartDate: String? = null
    private var maxPerson: Int? = null
    private var preferredTeamIdList: Array<Int>? = null

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
        initRecyclerView()
        Log.e("POST SIZE", postList.size.toString())
        homeViewModel.getBoardListResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                if (response.boardInfoList.isEmpty() && response.isLast && currentPage == 0) {
                    Log.e("게시글 목록 더이상 없음", response.boardInfoList.size.toString())
                    binding.rvHomePosts.visibility = View.GONE
                    binding.layoutHomeNoList.visibility = View.VISIBLE
                } else {
                    binding.rvHomePosts.visibility = View.VISIBLE
                    binding.layoutHomeNoList.visibility = View.GONE
                    Log.e("게시글 목록 존재", response.boardInfoList.size.toString())

                    if (currentPage == 0 && isFirstLoad) {
                        postList.clear()
                        isFirstLoad = false
                        Log.i("LIST CLEARED", "CLEARED")
                    }

                    if (response.totalElements > postList.size) {
                        postList.addAll(response.boardInfoList)
                        postList.forEach {
                            Log.e("LIST", "${it.boardId}")
                        }
                    }
                    Log.e("POST SIZE2", postList.size.toString())
                    val adapter = binding.rvHomePosts.adapter as HomePostAdapter
                    adapter.updatePostList(postList)
                    isLastPage = response.isLast
                    isLoading = false
                    currentPage++
                }
            }
        }

        loadBoardList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        currentPage = 0
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

    private fun loadBoardList() {
        if (isLoading || isLastPage) return
        isLoading = true
        homeViewModel.getBoardList(
            gameStartDate,
            maxPerson,
            preferredTeamIdList,
            currentPage,
        )
    }

    private fun initDateFilter() {
        binding.hfvHomeDateFilter.setOnClickListener {
            val dateFilterBottomSheet = HomeDateFilterBottomSheetFragment(gameStartDate)
            dateFilterBottomSheet.setOnDateFilterSelectedListener(this@HomeFragment)
            dateFilterBottomSheet.show(requireActivity().supportFragmentManager, dateFilterBottomSheet.tag)
        }
        if (gameStartDate != null) {
            binding.hfvHomeDateFilter.setDateFilterText(gameStartDate)
            binding.hfvHomeDateFilter.setFilterTextColor(true)
        }
    }

    private fun initTeamFilter() {
        binding.hfvHomeTeamFilter.setOnClickListener {
            val teamFilterBottomSheet = HomeTeamFilterBottomSheetFragment(preferredTeamIdList)
            teamFilterBottomSheet.setOnClubSelectedListener(this@HomeFragment)
            teamFilterBottomSheet.show(requireActivity().supportFragmentManager, teamFilterBottomSheet.tag)
        }
        if (preferredTeamIdList != null) {
            binding.hfvHomeTeamFilter.setClubFilterText(preferredTeamIdList)
            binding.hfvHomeTeamFilter.setFilterTextColor(true)
        }
    }

    private fun initHeadCountFilter() {
        binding.hfvHomeMemberCountFilter.setOnClickListener {
            val headCountFilterBottomSheet = HomeHeadCountFilterBottomSheetFragment(maxPerson)
            headCountFilterBottomSheet.setOnPersonFilterSelected(this@HomeFragment)
            headCountFilterBottomSheet.show(requireActivity().supportFragmentManager, headCountFilterBottomSheet.tag)
        }
        if (maxPerson != null) {
            binding.hfvHomeMemberCountFilter.setFilterTextColor(true)
            binding.hfvHomeMemberCountFilter.setPersonFilterText(maxPerson)
        }
    }

    private fun initRecyclerView() {
        binding.rvHomePosts.apply {
            adapter = HomePostAdapter(requireContext(), layoutInflater, this@HomeFragment)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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

                        if (lastVisibleItemPosition + 1 >= itemTotalCount && !isLastPage && !isLoading) { // 새로운 목록 불러와야함
                            loadBoardList()
                        }
                    }
                },
            )
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
        currentPage = 0
        postList.clear()
        homeViewModel.getBoardList(
            gameStartDate,
            maxPerson,
            preferredTeamIdList,
            currentPage,
        )
        binding.hfvHomeDateFilter.setDateFilterText(gameStartDate)
        binding.hfvHomeDateFilter.setFilterTextColor(gameStartDate != null)
    }

    override fun onClubFilterSelected(clubIdList: Array<Int>?) {
        preferredTeamIdList = clubIdList
        currentPage = 0
        postList.clear()
        homeViewModel.getBoardList(
            gameStartDate,
            maxPerson,
            preferredTeamIdList,
            currentPage,
        )
        binding.hfvHomeTeamFilter.setClubFilterText(preferredTeamIdList)
        binding.hfvHomeTeamFilter.setFilterTextColor(preferredTeamIdList != null)
    }

    override fun onPersonFilterSelected(count: Int?) {
        maxPerson = count
        currentPage = 0
        postList.clear()
        homeViewModel.getBoardList(
            gameStartDate,
            maxPerson,
            preferredTeamIdList,
            currentPage,
        )
        binding.hfvHomeMemberCountFilter.setFilterTextColor(count != null)
        binding.hfvHomeMemberCountFilter.setPersonFilterText(count)
    }
}
