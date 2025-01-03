package com.catchmate.presentation.view.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.catchmate.domain.model.Content
import com.catchmate.domain.model.GetReceivedNotificationListResponse
import com.catchmate.presentation.databinding.ItemNotificationBinding
import de.hdodenhof.circleimageview.CircleImageView

class NotificationAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private var notificationList: MutableList<Content> = mutableListOf()

    fun updateNotificationList(newList: List<Content>) {
        notificationList = newList.toMutableList()
        notifyDataSetChanged()
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
//        Glide.with(context)
//            .load(notificationList[position].)
    }
}
