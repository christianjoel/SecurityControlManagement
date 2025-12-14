package com.example.scm.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scm.R
import com.example.scm.data.model.MenuItems


class TaskStatusAdapter(
    val context: Activity,
    val catList: ArrayList<MenuItems>,
    val clickCat: ClickCat
) :
    RecyclerView.Adapter<TaskStatusAdapter.DataViewHolder>() {
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)

        fun bind(proList: MenuItems) {
            tv_title.text = proList.itemName



          //  AppHelper.loadImagewithLoader(context, product_image, proList.itemImage, progress_bar)
            itemView.setOnClickListener {
                clickCat.onClick(proList, tv_title)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.taskstatus_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(catList[position])
    }

    override fun getItemCount(): Int {
        return catList.size
    }


    interface ClickCat {
        fun onClick(catList: MenuItems, vararg views: View)
    }
}
