package com.kashapovrush.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.R
import com.kashapovrush.domain.Part
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PartAdapter : ListAdapter<Part, PartAdapter.PartViewHolder>(PartItemDiffCallback()) {

    var onPartClickListener: ((Part) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_main,
            parent,
            false
        )
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        val part = getItem(position)
        holder.tvNumber.text = part.number.toString()
        holder.tvName.text = part.name
        holder.tvCount.text = part.count.toString()

        holder.view.setOnClickListener {
            onPartClickListener?.invoke(part)
        }
    }

    class PartViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvNumber = view.findViewById<TextView>(R.id.tv_number)
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }
}