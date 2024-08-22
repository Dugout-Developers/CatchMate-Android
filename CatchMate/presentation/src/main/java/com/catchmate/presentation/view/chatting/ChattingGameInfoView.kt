package com.catchmate.presentation.view.chatting

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ViewChattingGameInfoBinding

class ChattingGameInfoView(
    context: Context,
    attrs: AttributeSet,
) : ConstraintLayout(context, attrs) {
    private val binding: ViewChattingGameInfoBinding by lazy {
        ViewChattingGameInfoBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )
    }

    init {
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChattingGameInfoView)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val date = typedArray.getText(R.styleable.ChattingGameInfoView_chattingGameInfoDateText)
        val time = typedArray.getText(R.styleable.ChattingGameInfoView_chattingGameInfoTimeText)
        val place = typedArray.getText(R.styleable.ChattingGameInfoView_chattingGameInfoPlaceText)
        val homeTeamImage = typedArray.getResourceId(R.styleable.ChattingGameInfoView_chattingGameInfoHomeTeamImage, 0)
        val awayTeamImage = typedArray.getResourceId(R.styleable.ChattingGameInfoView_chattingGameInfoHomeTeamImage, 0)

        binding.tvChattingGameInfoDate.text = date
        binding.tvChattingGameInfoTime.text = time
        binding.tvChattingGameInfoPlace.text = place

        if (homeTeamImage != 0) {
            binding.ivChattingGameInfoHomeTeam.setImageResource(homeTeamImage)
        }

        if (awayTeamImage != 0) {
            binding.ivChattingGameInfoAwayTeam.setImageResource(awayTeamImage)
        }

        typedArray.recycle()
    }
}
