package com.example.scm.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scm.R
import com.example.scm.data.model.MenuItems


class ScannerAdapter(
    val context: Activity,
    val catList: ArrayList<MenuItems>,
    val clickCat: ClickCat
) :
    RecyclerView.Adapter<ScannerAdapter.DataViewHolder>() {
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_title: TextView = itemView.findViewById(R.id.product_title)

        var product_image: ImageView = itemView.findViewById(R.id.product_image)
        var progress_bar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun bind(proList: MenuItems) {
            product_title.text = proList.itemName


            //  AppHelper.loadImagewithLoader(context, product_image, proList.itemImage, progress_bar)
            itemView.setOnClickListener {
                clickCat.onClick(proList, product_title)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.scanner_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(catList.get(position))
    }

    override fun getItemCount(): Int {
        return catList.size
    }


    interface ClickCat {
        fun onClick(catList: MenuItems, vararg views: View)
    }
}
