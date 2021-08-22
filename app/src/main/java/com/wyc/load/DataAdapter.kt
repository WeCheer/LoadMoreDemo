package com.wyc.load

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *作者： wyc
 * <p>
 * 创建时间： 2021/7/27 17:57
 * <p>
 * 文件名字： com.wyc.load
 * <p>
 * 类的介绍：
 */
class DataAdapter(
    private val mDataList: MutableList<DataBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mCheckMap = HashMap<String, Boolean>()

    init {
        mDataList.forEach {
            mCheckMap[it.flag] = false
        }
    }

    fun addData(dataList: MutableList<DataBean>) {
        mDataList.addAll(dataList)
        dataList.forEach {
            mCheckMap[it.flag] = false
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NormalViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_check_view, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalViewHolder) {
            val bean = mDataList[position]
            holder.itemCheckTv.text = bean.title

            holder.itemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                mCheckMap[bean.flag] = isChecked
                notifyItemChanged(position)
            }

            holder.itemCheckBox.isChecked = mCheckMap.isNotEmpty()
                    && mCheckMap[bean.flag]!!
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    private inner class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemCheckTv: TextView = itemView.findViewById(R.id.item_check_tv)
        val itemCheckBox: CheckBox = itemView.findViewById(R.id.item_check_box)
    }
}