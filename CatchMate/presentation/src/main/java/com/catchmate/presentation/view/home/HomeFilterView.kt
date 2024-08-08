package com.catchmate.presentation.view.home

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ViewHomeFilterBinding

class HomeFilterView : ConstraintLayout {
    lateinit var layoutFilter: ConstraintLayout
    lateinit var tvFilterName: TextView
    lateinit var ivFilterDropdown: ImageView
    constructor(context: Context) : super(context) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    private val binding: ViewHomeFilterBinding by lazy {
        ViewHomeFilterBinding.bind(
            LayoutInflater.from(context).inflate(
                R.layout.view_home_filter,
                this,
                false,
            ),
        )
    }

    private fun initView() {
        addView(binding.root)
        layoutFilter = binding.layoutFilter
        tvFilterName = binding.tvFilterName
        ivFilterDropdown = binding.ivFilterDropdown
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeFilterView)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val homeFilterLayoutColor = typedArray.getColor(R.styleable.HomeFilterView_homeFilterLayoutColor, -1)
        val homeFilterNameText = typedArray.getText(R.styleable.HomeFilterView_homeFilterNameText)
        val homeFilterImage = typedArray.getResourceId(R.styleable.HomeFilterView_homeFilterImage, -1)

        ViewCompat.setBackgroundTintList(layoutFilter, ColorStateList.valueOf(homeFilterLayoutColor))
        tvFilterName.text = homeFilterNameText
        ivFilterDropdown.setImageResource(homeFilterImage)
    }
}
