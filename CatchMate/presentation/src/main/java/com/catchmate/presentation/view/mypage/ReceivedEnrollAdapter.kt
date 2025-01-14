package com.catchmate.presentation.view.mypage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.catchmate.domain.model.TestEnrollInfo
import com.catchmate.presentation.databinding.ItemReceivedEnrollBinding
import de.hdodenhof.circleimageview.CircleImageView

class ReceivedEnrollAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
) : RecyclerView.Adapter<ReceivedEnrollAdapter.ReceivedEnrollViewHolder>() {
    private var enrollList: MutableList<TestEnrollInfo> = mutableListOf()

    fun updateEnrollList(newList: List<TestEnrollInfo>) {
        enrollList = newList.toMutableList()
        notifyDataSetChanged()
    }

    inner class ReceivedEnrollViewHolder(
        itemBinding: ItemReceivedEnrollBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        val ivEnrollUserProfile: CircleImageView = itemBinding.ivItemReceivedEnrollProfile
        val tvEnrollUserNickname: TextView = itemBinding.tvItemReceivedEnrollNickname
        val tvEnrollUserCheerTeam: TextView = itemBinding.tvItemReceivedEnrollTeamBadge
        val tvEnrollUserGender: TextView = itemBinding.tvItemReceivedEnrollGenderBadge
        val tvEnrollUserAge: TextView = itemBinding.tvItemReceivedEnrollAgeBadge
        val tvEnrollSavedDateTime: TextView = itemBinding.tvItemReceivedEnrollSavedDateTime
        val edtEnrollDescription: EditText = itemBinding.edtItemReceivedEnrollDescription
        val tvEnrollReject: TextView = itemBinding.tvItemReceivedEnrollReject
        val tvEnrollAccept: TextView = itemBinding.tvItemReceivedEnrollAccept
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReceivedEnrollViewHolder {
        val itemBinding = ItemReceivedEnrollBinding.inflate(layoutInflater)
        itemBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        return ReceivedEnrollViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = enrollList.size

    override fun onBindViewHolder(
        holder: ReceivedEnrollViewHolder,
        position: Int,
    ) {
        val info = enrollList[position]
        holder.apply {
            tvEnrollUserNickname.text = info.nickName
            tvEnrollUserCheerTeam.text = info.teamName
            tvEnrollUserGender.text = info.gender
            tvEnrollUserAge.text = info.age
            tvEnrollSavedDateTime.text = info.date
            edtEnrollDescription.setText(info.description)

            tvEnrollReject.setOnClickListener {
                Log.e("REJECT", "$position")
            }
            tvEnrollAccept.setOnClickListener {
                Log.e("ACCEPT", "$position")
            }
        }
    }
}
