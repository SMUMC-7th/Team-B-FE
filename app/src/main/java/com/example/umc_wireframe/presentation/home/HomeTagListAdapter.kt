package com.example.umc_wireframe.presentation.home

import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.ItemRvRecommendedtagsBinding

class HomeTagListAdapter
    : ListAdapter<String, HomeTagListAdapter.ViewHolder>(HomeTagDiffUtil()) {

    inner class ViewHolder(
        private val binding: ItemRvRecommendedtagsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.tvItemTag.text = "#"+tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvRecommendedtagsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HomeTagDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}