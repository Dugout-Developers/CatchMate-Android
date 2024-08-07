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
        ViewTeamButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.view_team_button, this, false)
        )
    }

    private fun initView() {
        addView(binding.root)
        teamImageView = binding.ivTeamButton
        teamToggleButton = binding.toggleTeamButton
        teamTextView = binding.tvTeamButton

        teamTextView.setTextColor(ContextCompat.getColor(context, R.color.grey700))
        teamToggleButton.setOnCheckedChangeListener { _, isChecked ->
            val textColor = if (isChecked) {
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
        val teamToggleBg = typedArray.getResourceId(R.styleable.TeamButtonView_teamToggleBg, -1)
        val teamLogoImage = typedArray.getResourceId(R.styleable.TeamButtonView_teamLogoImage, -1)

        teamTextView.text = teamName
        teamToggleButton.setBackgroundResource(teamToggleBg)
        teamImageView.setImageResource(teamLogoImage)

        typedArray.recycle()
    }
}
