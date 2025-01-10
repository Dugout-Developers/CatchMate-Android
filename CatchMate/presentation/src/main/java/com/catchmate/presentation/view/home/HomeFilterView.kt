package com.catchmate.presentation.view.home

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.ViewHomeFilterBinding
import com.catchmate.presentation.util.DateUtils

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

    fun setFilterText(str: String?) {
        if (str != null) {
            binding.tvFilterName.text = DateUtils.formatDateToFilterDate(str)
        } else {
            binding.tvFilterName.text = ContextCompat.getString(context, R.string.home_filter_date)
        }
    }

    fun setFilterTextColor(isSelected: Boolean) {
        val color = if (isSelected) {
            binding.layoutFilter.setBackgroundResource(R.drawable.shape_home_filter_selected_r8)
            ContextCompat.getColor(context, R.color.brand500)
        } else {
            binding.layoutFilter.setBackgroundResource(R.drawable.shape_home_filter_unselected_r8)
            ContextCompat.getColor(context, R.color.grey700)
        }
        binding.tvFilterName.setTextColor(color)
        binding.ivFilterDropdown.imageTintList = ColorStateList.valueOf(color)
    }
}
