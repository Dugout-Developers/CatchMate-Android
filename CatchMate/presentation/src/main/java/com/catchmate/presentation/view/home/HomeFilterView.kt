package com.catchmate.presentation.view.home

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ViewHomeFilterBinding

class HomeFilterView(
    context: Context,
    attrs: AttributeSet,
) : ConstraintLayout(context, attrs) {
    private val binding: ViewHomeFilterBinding by lazy {
        ViewHomeFilterBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )
    }

    init {
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeFilterView)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val homeFilterNameText = typedArray.getText(R.styleable.HomeFilterView_homeFilterNameText)
        val homeFilterImage = typedArray.getResourceId(R.styleable.HomeFilterView_homeFilterImage, -1)

        binding.tvFilterName.text = homeFilterNameText
        binding.ivFilterDropdown.setImageResource(homeFilterImage)
    }
}
