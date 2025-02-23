package com.catchmate.presentation.view.notification

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.catchmate.domain.model.notification.NotificationInfo
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ItemNotificationBinding
import com.catchmate.presentation.interaction.OnListItemAllRemovedListener
import com.catchmate.presentation.interaction.OnNotificationItemClickListener
import com.catchmate.presentation.interaction.OnNotificationItemSwipeListener
import com.catchmate.presentation.util.DateUtils
import de.hdodenhof.circleimageview.CircleImageView

class NotificationAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val itemClickListener: OnNotificationItemClickListener,
    private val itemSwipeListener: OnNotificationItemSwipeListener,
    private val itemAllRemovedListener: OnListItemAllRemovedListener,
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private var notificationList: MutableList<NotificationInfo> = mutableListOf()

    fun updateNotificationList(newList: List<NotificationInfo>) {
        notificationList = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun updateSelectedNotification(pos: Int) {
        notificationList[pos].read = true
        notifyItemChanged(pos)
    }

    fun removeItem(pos: Int) {
        Log.d("알림 어댑터", "remove Item : $pos")
        notificationList.removeAt(pos)
        notifyItemRemoved(pos)
        if (notificationList.size == 0) itemAllRemovedListener.onListItemAllRemoved()
    }

    fun swipeItem(
        pos: Int,
        notificationId: Long,
    ) {
        itemSwipeListener.onNotificationItemSwipe(pos, notificationId)
    }

    inner class ViewHolder(
        itemBinding: ItemNotificationBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        val layoutNotification: ConstraintLayout
        val ivNotificationItemProfile: CircleImageView
        val tvNotificationItemTitle: TextView
        val tvNotificationItemDate: TextView
        val tvNotificationItemTime: TextView
        val tvNotificationItemPlace: TextView

        init {
            layoutNotification = itemBinding.layoutNotification
            ivNotificationItemProfile = itemBinding.ivNotificationItemProfile
            tvNotificationItemTitle = itemBinding.tvNotificationItemTitle
            tvNotificationItemDate = itemBinding.tvNotificationItemDate
            tvNotificationItemTime = itemBinding.tvNotificationItemTime
            tvNotificationItemPlace = itemBinding.tvNotificationItemPlace

            itemBinding.root.setOnClickListener {
                val pos = absoluteAdapterPosition
                itemClickListener.onNotificationItemClick(notificationList[pos].notificationId, pos)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val itemBinding = ItemNotificationBinding.inflate(layoutInflater)
        itemBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = notificationList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val info = notificationList[position]
        Glide
            .with(context)
            .load(info.senderProfileImageUrl)
            .into(holder.ivNotificationItemProfile)
        holder.tvNotificationItemTitle.text = info.body
        val dateTime: Pair<String, String> = DateUtils.formatISODateTimeToDateTime(info.boardInfo.gameInfo.gameStartDate!!)
        holder.tvNotificationItemDate.text = dateTime.first + " | "
        holder.tvNotificationItemTime.text = dateTime.second + " | "
        holder.tvNotificationItemPlace.text = info.boardInfo.gameInfo.location

        if (info.read) {
            holder.layoutNotification.setBackgroundColor(ContextCompat.getColor(context, R.color.grey50))
            holder.tvNotificationItemTitle.setTextColor(ContextCompat.getColor(context, R.color.grey500))
        } else {
            holder.layoutNotification.setBackgroundColor(ContextCompat.getColor(context, R.color.grey0))
            holder.tvNotificationItemTitle.setTextColor(ContextCompat.getColor(context, R.color.grey800))
        }
    }
}
