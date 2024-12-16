package com.example.umc_wireframe.presentation.community.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvCommentBinding

class PostDetailCommentListAdapter :
    PagingDataAdapter<PostDetailCommentItem, PostDetailCommentListAdapter.ViewHolder>(
        PostDetailCommentDiffUtil()
    ) {

    inner class ViewHolder(
        val binding: ItemRvCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostDetailCommentItem) = with(binding) {
            tvItemCommentWriter.text = item.writer
            tvItemCommentContent.text = item.content
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRvCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

}

class PostDetailCommentDiffUtil : DiffUtil.ItemCallback<PostDetailCommentItem>() {
    override fun areItemsTheSame(
        oldItem: PostDetailCommentItem,
        newItem: PostDetailCommentItem
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: PostDetailCommentItem,
        newItem: PostDetailCommentItem
    ): Boolean {
        return oldItem == newItem
    }

}