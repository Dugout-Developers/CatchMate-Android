package com.catchmate.presentation.view.support

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.catchmate.domain.model.support.PostInquiryRequest
import com.catchmate.presentation.R
import com.catchmate.presentation.databinding.FragmentServiceCenterInquiryBinding
import com.catchmate.presentation.view.base.BaseFragment
import com.catchmate.presentation.viewmodel.ServiceCenterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceCenterInquiryFragment : BaseFragment<FragmentServiceCenterInquiryBinding>(FragmentServiceCenterInquiryBinding::inflate) {
    private val inquiryType by lazy { arguments?.getString("inquiryType") ?: "" }
    private val serviceCenterViewModel: ServiceCenterViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initView() {
        binding.apply {
            imgbtnBackServiceCenterInquiry.setOnClickListener {
                findNavController().popBackStack()
            }

            layoutFooterServiceCenterInquiry.btnFooterOne.apply {
                text = getString(R.string.service_center_inquiry_apply_btn)
                setOnClickListener {
                    val request =
                        PostInquiryRequest(
                            inquiryType,
                            edtContentServiceCenterInquiry.text.toString(),
                        )
                    serviceCenterViewModel.postInquiry(request)
                }
            }

            edtContentServiceCenterInquiry.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        val currentLength = s?.length ?: 0
                        tvContentLetterCountServiceCenterInquiry.text = currentLength.toString()
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val inputText = s?.toString()?.trim()
                        layoutFooterServiceCenterInquiry.btnFooterOne.isEnabled = !inputText.isNullOrEmpty()
                    }
                }
            )
        }
    }

    private fun initViewModel() {
        serviceCenterViewModel.postInquiryResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                Toast.makeText(requireContext(), R.string.service_center_inquiry_complete, Toast.LENGTH_SHORT).show()
                binding.edtContentServiceCenterInquiry.setText(R.string.all_empty_string)
            }
        }
    }
}
