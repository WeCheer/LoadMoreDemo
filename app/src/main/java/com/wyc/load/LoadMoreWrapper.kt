package com.wyc.load

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView


/**
 *作者： wyc
 * <p>
 * 创建时间： 2021/7/27 17:33
 * <p>
 * 文件名字： com.wyc.load
 * <p>
 * 类的介绍：
 */
class LoadMoreWrapper(
    private val mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val FOOTER_VIEW = 10001
        const val NORMAL_VIEW = 10002
        const val FOOTER_START = 10003
        const val FOOTER_END = 10004
        const val FOOTER_NO_MORE = 10005
    }

    private var mFooterState = 0

    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount) {
            return FOOTER_VIEW
        }
        return NORMAL_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NORMAL_VIEW -> {
                mAdapter.onCreateViewHolder(parent, viewType)
            }
            else -> {
                ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_more, parent, false))
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return if (getItemViewType(position) == FOOTER_VIEW) manager.spanCount else 1
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            when (mFooterState) {
                FOOTER_START -> {
                    holder.textFoot.visibility = View.VISIBLE
                    holder.progressBar.visibility = View.VISIBLE
                }
                FOOTER_END -> {
                    holder.textFoot.visibility = View.GONE
                    holder.progressBar.visibility = View.GONE
                }
                FOOTER_NO_MORE -> {
                    holder.progressBar.visibility = View.GONE
                    holder.textFoot.text = "没有更多数据了"
                }
            }
        } else {
            mAdapter.onBindViewHolder(holder, position)
        }
    }

    fun setFooterState(state: Int) {
        mFooterState = state
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mAdapter.itemCount + 1
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textFoot: TextView = itemView.findViewById(R.id.progressText)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }
}