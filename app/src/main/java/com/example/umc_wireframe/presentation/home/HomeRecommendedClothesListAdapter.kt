package com.example.umc_wireframe.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvRecommenedclothesBinding

class HomeRecommendedClothesListAdapter
    : ListAdapter<Int, HomeRecommendedClothesListAdapter.ViewHolder>(HomeRecommendedDiffUtil()) {

    inner class ViewHolder(
        private val binding: ItemRvRecommenedclothesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resId: Int) {
            binding.ivItemRecommended.setImageResource(resId)
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

class HomeRecommendedDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}

