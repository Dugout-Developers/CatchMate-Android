package com.catchmate.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.BoardListResponse
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ItemHomePostBinding
import com.catchmate.presentation.util.ResourceUtil

class HomePostAdapter(
    private val layoutInflater: LayoutInflater,
) : RecyclerView.Adapter<HomePostAdapter.HomePostViewHolder>() {
    private var postList: List<BoardListResponse> = emptyList()

    fun updatePostList(newList: List<BoardListResponse>) {
        this.postList = newList
        notifyDataSetChanged()
    }

    inner class HomePostViewHolder(itemBinding: ItemHomePostBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val cvItemLayout: CardView
        val tvItemCount: TextView
        val tvItemDate: TextView
        val tvItemTime: TextView
        val tvItemPlace: TextView
        val tvItemTitle: TextView
        val ivItemHomeTeamBg: ImageView
        val ivItemAwayTeamBg: ImageView
        val ivItemHomeTeamLogo: ImageView
        val ivItemAwayTeamLogo: ImageView

        init {
            cvItemLayout = itemBinding.cvItemHomePost
            tvItemCount = itemBinding.tvItemHomePostMemberCount
            tvItemDate = itemBinding.tvItemHomePostDate
            tvItemTime = itemBinding.tvItemHomePostTime
            tvItemPlace = itemBinding.tvItemHomePostPlace
            tvItemTitle = itemBinding.tvItemHomePostTitle
            ivItemHomeTeamBg = itemBinding.ivItemHomePostHomeTeamBg
            ivItemAwayTeamBg = itemBinding.ivItemHomePostAwayTeamBg
            ivItemHomeTeamLogo = itemBinding.ivItemHomePostHomeTeamLogo
            ivItemAwayTeamLogo = itemBinding.ivItemHomePostAwayTeamLogo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostViewHolder {
        val itemBinding = ItemHomePostBinding.inflate(layoutInflater)
        itemBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        return HomePostViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: HomePostViewHolder, position: Int) {
        val homeBoard = postList[position]
        holder.apply {
            tvItemCount.text = "${homeBoard.currentPerson}/${homeBoard.maxPerson}"
            tvItemDate.text = homeBoard.gameDate
            tvItemTime.text = homeBoard.gameDate
            tvItemPlace.text = homeBoard.location
            tvItemTitle.text = homeBoard.title
            ivItemHomeTeamBg.setImageResource(R.drawable.shape_all_rect_r8_grey50)
            ivItemAwayTeamBg.setImageResource(R.drawable.shape_all_rect_r8_grey0)
            ivItemHomeTeamLogo.setImageResource(ResourceUtil.convertTeamLogo(homeBoard.homeTeam))
            ivItemAwayTeamLogo.setImageResource(ResourceUtil.convertTeamLogo(homeBoard.awayTeam))
        }
    }
}
