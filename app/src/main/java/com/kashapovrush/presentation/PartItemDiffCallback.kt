package com.kashapovrush.presentation

import androidx.recyclerview.widget.DiffUtil
import com.kashapovrush.domain.Part

class PartItemDiffCallback: DiffUtil.ItemCallback<Part>() {

    override fun areItemsTheSame(oldItem: Part, newItem: Part): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Part, newItem: Part): Boolean {
        return oldItem == newItem
    }
}