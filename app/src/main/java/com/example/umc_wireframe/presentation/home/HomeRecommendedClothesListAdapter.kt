package com.example.umc_wireframe.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_wireframe.databinding.ItemRvRecommenedclothesBinding

class HomeRecommendedClothesListAdapter
    : ListAdapter<String, HomeRecommendedClothesListAdapter.ViewHolder>(HomeRecommendedDiffUtil()) {

    inner class ViewHolder(
        private val binding: ItemRvRecommenedclothesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            Glide.with(binding.root)
                .load(url)
                .into(binding.ivItemRecommended)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvRecommenedclothesBinding.inflate(
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

class HomeRecommendedDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

