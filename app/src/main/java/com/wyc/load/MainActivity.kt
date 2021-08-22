package com.wyc.load

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DataAdapter(getData(0))
        val loadMoreWrapper = LoadMoreWrapper(adapter) // 封装后的Adapter
        recyclerView.adapter = loadMoreWrapper
        recyclerView.addOnScrollListener(object : EndRecyclerOnScrollListener() { // 添加监听事件
            override fun loadMore() { // 加载逻辑
                setFlag(1)  // 设置flag = 1，向上滑动，监听事件会继续触发，但是不会继续加载数据
                loadMoreWrapper.setFooterState(LoadMoreWrapper.FOOTER_START)
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        if (adapter.itemCount < 100) {
                            runOnUiThread {
                                adapter.addData(getData(adapter.itemCount + 1))
                                loadMoreWrapper.setFooterState(LoadMoreWrapper.FOOTER_END)
                                setFlag(0) // 此次数据加载完毕，设置flag = 0，以便下次数据可以加载
                            }
                        } else {
                            runOnUiThread {
                                loadMoreWrapper.setFooterState(LoadMoreWrapper.FOOTER_NO_MORE)
                            }
                        }
                    }
                }, 1000)
            }
        })
    }


    private fun getData(index: Int): MutableList<DataBean> {
        val data = mutableListOf<DataBean>()
        for (index in (index)..(index + 20)) {
            val bean = DataBean(index.toString(), index.toString())
            data.add(bean)
        }
        return data
    }
}
