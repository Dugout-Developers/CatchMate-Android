package com.catchmate.presentation.view.chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.catchmate.domain.model.chatting.ChatRoomInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ItemChattingBinding
import com.catchmate.presentation.interaction.OnChattingRoomSelectedListener
import com.catchmate.presentation.util.DateUtils.formatLastChatTime
import com.catchmate.presentation.util.ResourceUtil.convertTeamColor
import com.catchmate.presentation.util.ResourceUtil.convertTeamLogo

class ChattingRoomListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val onChattingRoomSelectedListener: OnChattingRoomSelectedListener,
) : RecyclerView.Adapter<ChattingRoomListAdapter.ChattingRoomListViewHolder>() {
    private var chattingRoomList: MutableList<ChatRoomInfo> = mutableListOf()

    fun updateList(newList: List<ChatRoomInfo>) {
        chattingRoomList = newList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ChattingRoomListViewHolder(
        itemBinding: ItemChattingBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        val ivBg: ImageView = itemBinding.ivChattingItemBg
        val ivLogo: ImageView = itemBinding.ivChattingItemLogo
        val tvTitle: TextView = itemBinding.tvChattingItemTitle
        val tvPeopleCount: TextView = itemBinding.tvChattingItemPeopleCount
        val tvNewBadge: TextView = itemBinding.tvChattingItemNew
        val tvLastChatTime: TextView = itemBinding.tvChattingItemTime
        val tvLastChat: TextView = itemBinding.tvChattingItemLastChat
        val tvNewChatCount: TextView = itemBinding.tvChattingItemUnreadMessageCount

        init {
            itemBinding.root.setOnClickListener {
                val chatRoomInfo = chattingRoomList[absoluteAdapterPosition]
                onChattingRoomSelectedListener.onChattingRoomSelected(chatRoomInfo)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChattingRoomListViewHolder {
        val itemBinding = ItemChattingBinding.inflate(layoutInflater)
        itemBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        return ChattingRoomListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = chattingRoomList.size

    override fun onBindViewHolder(
        holder: ChattingRoomListViewHolder,
        position: Int,
    ) {
        val chatRoomInfo = chattingRoomList[position]

        holder.apply {
            val logoResource = convertTeamLogo(chatRoomInfo.boardInfo.gameInfo.homeClubId)
            Glide
                .with(context)
                .load(logoResource)
                .into(ivLogo)
            DrawableCompat
                .setTint(
                    ivBg.drawable,
                    convertTeamColor(
                        context,
                        chatRoomInfo.boardInfo.gameInfo.homeClubId,
                        true,
                        "chattingHome",
                    ),
                )
            tvTitle.text = chatRoomInfo.boardInfo.title
            if (chatRoomInfo.lastMessageAt == null && chatRoomInfo.lastMessageContent == null) {
                tvNewBadge.visibility = View.VISIBLE
                tvPeopleCount.visibility = View.GONE
                tvLastChat.text = context.getString(R.string.chatting_start_message)
                tvLastChatTime.text = "방금"
            } else {
                tvNewBadge.visibility = View.GONE
                tvPeopleCount.visibility = View.VISIBLE
                tvPeopleCount.text = chatRoomInfo.participantCount.toString()
                tvLastChat.text = chatRoomInfo.lastMessageContent
                tvLastChatTime.text = formatLastChatTime(chatRoomInfo.lastMessageAt!!)
            }
        }
    }
}
