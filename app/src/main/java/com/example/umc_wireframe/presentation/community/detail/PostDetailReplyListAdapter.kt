package com.example.umc_wireframe.presentation.community.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvReplyBinding

class PostDetailReplyListAdapter :
    ListAdapter<ReplyItem, PostDetailReplyListAdapter.ViewHolder>(
        PostDetailReplyDiffUtil()
    ) {
    inner class ViewHolder(
        val binding: ItemRvReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReplyItem) = with(binding) {
            tvItemReplyWriter.text = item.writer
            tvItemReplyContent.text = item.content

            if (item.report) tvItemReplyBlur.visibility = View.VISIBLE
            else tvItemReplyBlur.visibility = View.GONE

            tvItemReplyBlur.setOnClickListener {
                tvItemReplyBlur.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRvReplyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

}

class PostDetailReplyDiffUtil : DiffUtil.ItemCallback<ReplyItem>() {
    override fun areItemsTheSame(
        oldItem: ReplyItem,
        newItem: ReplyItem
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: ReplyItem,
        newItem: ReplyItem
    ): Boolean {
        return oldItem == newItem
    }

}
