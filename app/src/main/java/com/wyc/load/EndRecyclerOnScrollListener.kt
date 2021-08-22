package com.wyc.load

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *作者： wyc
 * <p>
 * 创建时间： 2021/7/27 18:14
 * <p>
 * 文件名字： com.wyc.load
 * <p>
 * 类的介绍：
 */
abstract class EndRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var flag = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layout = recyclerView.layoutManager as LinearLayoutManager
        val lastPositionCompletely = layout.findLastCompletelyVisibleItemPosition()
        if (lastPositionCompletely == layout.itemCount - 1 && flag == 0) {
            loadMore()
        }
    }

    abstract fun loadMore()

    fun setFlag(flag: Int) {    // 设置标记防止多次向上滑动，多次调用 loadMore()
        this.flag = flag
    }
}