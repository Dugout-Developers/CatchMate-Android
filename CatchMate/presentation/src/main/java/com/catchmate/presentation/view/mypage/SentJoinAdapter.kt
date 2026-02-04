package com.catchmate.presentation.view.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.enroll.EnrollInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ItemHomePostBinding
import com.catchmate.presentation.interaction.OnPostItemClickListener
import com.catchmate.presentation.util.DateUtils
import com.catchmate.presentation.util.ResourceUtil

class SentJoinAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val onPostItemClickListener: OnPostItemClickListener,
) : RecyclerView.Adapter<SentJoinAdapter.SentJoinViewHolder>() {
    private var enrollInfoList: MutableList<EnrollInfo> = mutableListOf()

    fun updateList(newList: List<EnrollInfo>) {
        enrollInfoList = newList.toMutableList()
        notifyDataSetChanged()
    }

    inner class SentJoinViewHolder(
        itemBinding: ItemHomePostBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SentJoinViewHolder {
        val itemBinding = ItemHomePostBinding.inflate(layoutInflater)
        itemBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        return SentJoinViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = enrollInfoList.size

    override fun onBindViewHolder(
        holder: SentJoinViewHolder,
        position: Int,
    ) {
        val enrollInfo = enrollInfoList[position]
        holder.apply {
            if (enrollInfo.boardResponse.currentPerson == enrollInfo.boardResponse.maxPerson) {
                tvItemCount.text = "${enrollInfo.boardResponse.currentPerson}/${enrollInfo.boardResponse.maxPerson} 마감"
                tvItemCount.setBackgroundResource(R.drawable.shape_all_rect_r12_grey100)
                tvItemCount.setTextColor(ContextCompat.getColor(context, R.color.grey500))
            } else {
                tvItemCount.text = "${enrollInfo.boardResponse.currentPerson}/${enrollInfo.boardResponse.maxPerson}"
                tvItemCount.setBackgroundResource(R.drawable.shape_all_rect_r12_brand50)
                tvItemCount.setTextColor(ContextCompat.getColor(context, R.color.brand500))
            }

            val dateTimePair = DateUtils.formatISODateTime(enrollInfo.boardResponse.gameResponse.gameStartDate!!)
            tvItemDate.text = dateTimePair.first
            tvItemTime.text = dateTimePair.second
            tvItemPlace.text = enrollInfo.boardResponse.gameResponse.location
            tvItemTitle.text = enrollInfo.boardResponse.title

            val isCheerTeam =
                enrollInfo.boardResponse.gameResponse.homeClub
                    ?.clubId == enrollInfo.boardResponse.cheerClub.clubId

            ResourceUtil.setTeamViewResources(
                enrollInfo.boardResponse.gameResponse.homeClub
                    ?.clubId ?: 0,
                isCheerTeam,
                ivItemHomeTeamBg,
                ivItemHomeTeamLogo,
                "home",
                context,
            )
            ResourceUtil.setTeamViewResources(
                enrollInfo.boardResponse.gameResponse.awayClub
                    ?.clubId ?: 0,
                !isCheerTeam,
                ivItemAwayTeamBg,
                ivItemAwayTeamLogo,
                "home",
                context,
            )

            cvItemLayout.setOnClickListener {
                onPostItemClickListener.onPostItemClicked(enrollInfo.boardResponse.boardId)
            }
        }
    }
}
