package com.catchmate.presentation.view.onboarding

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ViewTeamButtonBinding

class TeamButtonView : ConstraintLayout {
    lateinit var teamImageView: ImageView
    lateinit var teamToggleButton: ToggleButton
    lateinit var teamTextView: TextView

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    private val binding: ViewTeamButtonBinding by lazy {
        ViewTeamButtonBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )
    }

    private fun initView() {
        teamImageView = binding.ivTeamButton
        teamToggleButton = binding.toggleTeamButton
        teamTextView = binding.tvTeamButton

        teamTextView.setTextColor(ContextCompat.getColor(context, R.color.grey700))
        teamToggleButton.setOnCheckedChangeListener { _, isChecked ->
            val textColor =
                if (isChecked) {
                    ContextCompat.getColor(context, R.color.brand500)
                } else {
                    ContextCompat.getColor(context, R.color.grey700)
                }
            teamTextView.setTextColor(textColor)
        }
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TeamButtonView)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val teamName = typedArray.getText(R.styleable.TeamButtonView_teamNameText)
        val teamToggleBg = typedArray.getResourceId(R.styleable.TeamButtonView_teamToggleBg, 0)
        val teamLogoImage = typedArray.getResourceId(R.styleable.TeamButtonView_teamLogoImage, 0)

        teamTextView.text = teamName
        if (teamToggleBg != 0) {
            teamToggleButton.setBackgroundResource(teamToggleBg)
        }

        if (teamLogoImage != 0) {
            teamImageView.setImageResource(teamLogoImage)
        }

        typedArray.recycle()
    }
}
