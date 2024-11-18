package com.example.umc_wireframe.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvHistorytagBinding

class HomeHistoryTagListAdapter
    : ListAdapter<String, HomeHistoryTagListAdapter.ViewHolder>(HomeHistoryTagDiffUtil()) {
    inner class ViewHolder(
        private val binding: ItemRvHistorytagBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(tag: String) {
            binding.tvItemTag.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRvHistorytagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}


class HomeHistoryTagDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
