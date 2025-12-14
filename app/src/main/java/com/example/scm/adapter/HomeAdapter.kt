package com.example.scm.adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.scm.R
import com.example.scm.data.model.MenuItems
import com.example.scm.utils.helper.ArrayHelper


class HomeAdapter(
    val context: Activity,
    val homeData: ArrayList<MenuItems>,
    val clickCat: ClickCat
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var task_assign: Int = 0
    private var task_status: Int = 1
    private var write_message: Int = 2

    interface ClickCat {
        fun onClick(homeData: ArrayList<MenuItems>, str: String)
    }

    inner class TaskAssign(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var item_rv: RecyclerView = itemView.findViewById(R.id.item_rv)
        private var title: TextView = itemView.findViewById(R.id.title)
        fun bind(proList: MenuItems) {

            var _text = proList.itemName
            if (_text != null) {
                _text = _text.replace("_", " ")
                title.text = _text
            }


            val adapter =
                ScannerAdapter(context, ArrayHelper.getTaskArray(), object : ScannerAdapter.ClickCat {
                    override fun onClick(catList: MenuItems, vararg views: View) {
                        val bundle = Bundle()
                        //     detailIntent.putExtra(ProductDetail.DATA, catList)
                        bundle.putString("title", catList.itemName)

                        context.findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.navigation_home, bundle)
                    }
                })
            item_rv.adapter = adapter
            item_rv.isNestedScrollingEnabled = false
        }
    }

    inner class TaskStatus(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var item_rv: RecyclerView = itemView.findViewById(R.id.item_rv)
        private var title: TextView = itemView.findViewById(R.id.title)
        fun bind(proList: MenuItems) {
            var _text = proList.itemName
            if (_text != null) {
                _text = _text.replace("_", " ")
                title.text = _text


            }


            val adapter =
                TaskStatusAdapter(context, ArrayHelper.getTaskStatusArray(), object : TaskStatusAdapter.ClickCat {
                    override fun onClick(catList: MenuItems, vararg views: View) {
                        val bundle = Bundle()
                        //     detailIntent.putExtra(ProductDetail.DATA, catList)
                        bundle.putString("title", catList.itemName)

                        context.findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.navigation_home, bundle)
                    }
                })
            item_rv.adapter = adapter
            item_rv.isNestedScrollingEnabled = false
        }
    }


    override fun getItemViewType(position: Int): Int {

        if (homeData[position].itemName == "Task Assign") {
            return task_assign
        } else if (homeData.get(position).itemName == "Task Status") {
            return task_status
        } else {
            return write_message
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            task_assign -> {
                return TaskAssign(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.taskassign_static, parent, false)
                )
            }
            task_status -> {
                return TaskStatus(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.task_status, parent, false)
                )
            }
            else -> {
                return TaskStatus(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.task_status, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == task_assign) {
            (holder as TaskAssign).bind(homeData[position])
        } else if (getItemViewType(position) == task_status) {
            (holder as TaskStatus).bind(homeData[position])
        }
    }


    override fun getItemCount(): Int {
        return homeData.size
    }

}
