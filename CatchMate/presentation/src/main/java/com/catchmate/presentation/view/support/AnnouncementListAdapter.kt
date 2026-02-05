package com.catchmate.presentation.view.support

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.support.NoticeListInfo
import com.catchmate.presentation.databinding.ItemAnnouncementBinding
import com.catchmate.presentation.interaction.OnAnnouncementItemClickListener
import com.catchmate.presentation.util.DateUtils.formatInquiryAnsweredDate

class AnnouncementListAdapter(
    private val onAnnouncementItemClickListener: OnAnnouncementItemClickListener,
) : ListAdapter<NoticeListInfo, AnnouncementListAdapter.AnnouncementViewHolder>(diffUtil) {
    inner class AnnouncementViewHolder(
        private val binding: ItemAnnouncementBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NoticeListInfo) {
            binding.tvTitleItemAnnouncement.text = data.title
            binding.tvTeamAndDateInfoAnnouncement.text = "${data.writerNickname} | ${formatInquiryAnsweredDate(data.createdAt)}"
            binding.cvItemAnnouncement.setOnClickListener {
                onAnnouncementItemClickListener.onAnnouncementItemClick(data.noticeId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AnnouncementViewHolder =
        AnnouncementViewHolder(
            ItemAnnouncementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: AnnouncementViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil =
            object : DiffUtil.ItemCallback<NoticeListInfo>() {
                override fun areItemsTheSame(
                    oldItem: NoticeListInfo,
                    newItem: NoticeListInfo,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: NoticeListInfo,
                    newItem: NoticeListInfo,
                ): Boolean = oldItem == newItem
            }
    }
}
