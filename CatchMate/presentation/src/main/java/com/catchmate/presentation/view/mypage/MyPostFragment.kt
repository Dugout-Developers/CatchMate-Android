package com.catchmate.presentation.view.mypage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.catchmate.domain.model.board.Writer
import com.catchmate.presentation.R

class MyPostFragment : Fragment() {
    private var writer: Writer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        writer = getUserInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_post, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Writer", "${writer?.userId} / ${writer?.nickName}")
    }

    private fun getUserInfo(): Writer? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("userInfo", Writer::class.java)
        } else {
            arguments?.getParcelable("userInfo") as Writer?
        }
}
