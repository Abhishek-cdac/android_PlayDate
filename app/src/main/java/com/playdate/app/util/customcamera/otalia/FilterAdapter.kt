package com.playdate.app.util.customcamera.otalia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.otaliastudios.cameraview.filter.Filters
import com.playdate.app.R

class FilterAdapter(var allFilters: Array<Filters>) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.camera_filters, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_filter_name.text = allFilters[position].toString()
    }

    var activity: CameraActivity? = null
    fun setOnClick(activity: CameraActivity?) {
        this.activity = activity
    }

    override fun getItemCount(): Int {
        return allFilters.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_filter_name: TextView

        init {
            txt_filter_name = itemView.findViewById(R.id.txt_filter_name)
            itemView.setOnClickListener { view: View? ->
                activity!!.filterClickIndex(
                    adapterPosition
                )
            }
        }
    }
}