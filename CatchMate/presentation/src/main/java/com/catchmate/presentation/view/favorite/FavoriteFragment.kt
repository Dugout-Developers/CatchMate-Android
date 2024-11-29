package com.catchmate.presentation.view.favorite

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
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentFavoriteBinding
import com.catchmate.presentation.interaction.OnPostItemAllRemovedListener
import com.catchmate.presentation.interaction.OnPostItemClickListener
import com.catchmate.presentation.interaction.OnPostItemToggleClickListener
import com.catchmate.presentation.viewmodel.FavoriteViewModel
import com.catchmate.presentation.viewmodel.LocalDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    Fragment(),
    OnPostItemClickListener,
    OnPostItemToggleClickListener,
    OnPostItemAllRemovedListener {
    private var _binding: FragmentFavoriteBinding? = null
    val binding get() = _binding!!

    private val localDataViewModel: LocalDataViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        getTokens()
        initHeader()
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTokens() {
        localDataViewModel.getAccessToken()
        localDataViewModel.getRefreshToken()
        localDataViewModel.accessToken.observe(viewLifecycleOwner) { accessToken ->
            if (accessToken != null) {
                this.accessToken = accessToken
                initViewModel()
            }
        }
        localDataViewModel.refreshToken.observe(viewLifecycleOwner) { refreshToken ->
            if (refreshToken != null) {
                this.refreshToken = refreshToken
            }
        }
    }

    private fun initHeader() {
        binding.layoutHeaderFavorite.apply {
            tvHeaderTextTitle.setText(R.string.favorite_title)
            imgbtnHeaderTextBack.visibility = View.GONE
        }
    }

    private fun initViewModel() {
        favoriteViewModel.getLikedBoard()
        favoriteViewModel.getLikedBoardResponse.observe(viewLifecycleOwner) { likedList ->
            if (likedList.isNotEmpty()) {
                binding.layoutFavoriteNoList.visibility = View.GONE
                binding.rvFavoritePost.visibility = View.VISIBLE
                val adapter = binding.rvFavoritePost.adapter as FavoritePostAdapter
                adapter.updateLikedList(likedList)
            } else {
                binding.layoutFavoriteNoList.visibility = View.VISIBLE
                binding.rvFavoritePost.visibility = View.GONE
            }
        }

        favoriteViewModel.navigateToLogin.observe(viewLifecycleOwner) { isTrue ->
            if (isTrue) {
                val navOptions =
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.favoriteFragment, true)
                        .build()
                findNavController().navigate(R.id.action_favoriteFragment_to_loginFragment, null, navOptions)
            }
        }

        favoriteViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("Reissue Error", it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvFavoritePost.apply {
            adapter =
                FavoritePostAdapter(
                    requireContext(),
                    layoutInflater,
                    this@FavoriteFragment,
                    this@FavoriteFragment,
                    this@FavoriteFragment,
                )
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onPostItemClicked(boardId: Long) {
        val bundle = Bundle()
        bundle.putLong("boardId", boardId)
        findNavController().navigate(R.id.action_favoriteFragment_to_readPostFragment, bundle)
    }

    override fun onPostItemToggleClicked(
        boardId: Long,
        position: Int,
    ) {
        favoriteViewModel.postBoardLike(boardId, FAVORITE_CANCEL_FLAG)
        val adapter = binding.rvFavoritePost.adapter as FavoritePostAdapter
        adapter.removeUnlikedPost(position)
    }

    override fun onPostItemAllRemoved() {
        binding.rvFavoritePost.visibility = View.GONE
        binding.layoutFavoriteNoList.visibility = View.VISIBLE
    }

    companion object {
        const val FAVORITE_CANCEL_FLAG = 0
    }
}
