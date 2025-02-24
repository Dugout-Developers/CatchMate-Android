package com.catchmate.presentation.view.support

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.presentation.databinding.ItemAnnouncementBinding

class AnnouncementListAdapter : ListAdapter<Pair<String, String>, AnnouncementListAdapter.AnnouncementViewHolder>(diffUtil) {
    inner class AnnouncementViewHolder(
        private val binding: ItemAnnouncementBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Pair<String, String>) {
            binding.tvTitleItemAnnouncement.text = data.first
            binding.tvTeamAndDateInfoAnnouncement.text = data.second
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
            object : DiffUtil.ItemCallback<Pair<String, String>>() {
                override fun areItemsTheSame(
                    oldItem: Pair<String, String>,
                    newItem: Pair<String, String>,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: Pair<String, String>,
                    newItem: Pair<String, String>,
                ): Boolean = oldItem == newItem
            }
    }
}
