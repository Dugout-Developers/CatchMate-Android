package com.catchmate.presentation.view.onboarding

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ViewCheerStyleButtonBinding

class CheerStyleButtonView : ConstraintLayout {
    lateinit var toggleCheerStyle: ToggleButton
    lateinit var tvCheerStyleName: TextView
    lateinit var tvCheerStyleExplain: TextView
    lateinit var ivCheerStyle: ImageView

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    private val binding: ViewCheerStyleButtonBinding by lazy {
        ViewCheerStyleButtonBinding.bind(
            LayoutInflater.from(context).inflate(
                R.layout.view_cheer_style_button,
                this,
                false,
            )
        )
    }

    private fun initView() {
        addView(binding.root)
        toggleCheerStyle = binding.toggleCheerStyle
        tvCheerStyleName = binding.tvCheerStyleName
        tvCheerStyleExplain = binding.tvCheerStyleExplain
        ivCheerStyle = binding.ivCheerStyle
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheerStyleButtonView)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val cheerStyleToggleBg = typedArray.getResourceId(R.styleable.CheerStyleButtonView_cheerStyleToggleBg, -1)
        val cheerStyleNameText = typedArray.getText(R.styleable.CheerStyleButtonView_cheerStyleNameText)
        val cheerStyleExplainText = typedArray.getText(R.styleable.CheerStyleButtonView_cheerStyleExplainText)
        val cheerStyleImage = typedArray.getResourceId(R.styleable.CheerStyleButtonView_cheerStyleImage, -1)

        tvCheerStyleName.text = cheerStyleNameText
        tvCheerStyleExplain.text = cheerStyleExplainText
        toggleCheerStyle.setBackgroundResource(cheerStyleToggleBg)
        ivCheerStyle.setImageResource(cheerStyleImage)
    }
}
