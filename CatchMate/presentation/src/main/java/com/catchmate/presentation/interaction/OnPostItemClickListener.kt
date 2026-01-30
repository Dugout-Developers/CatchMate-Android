package com.catchmate.presentation.interaction

interface OnPostItemClickListener {
    fun onPostItemClicked(boardId: Long)
}

// HomeFragment 전용 인터페이스
interface OnHomePostItemClickListener : OnPostItemClickListener {
    fun onPostItemClicked(
        boardId: Long,
        position: Int,
    )

    override fun onPostItemClicked(boardId: Long) {
        TODO("Not yet implemented")
    }
}
