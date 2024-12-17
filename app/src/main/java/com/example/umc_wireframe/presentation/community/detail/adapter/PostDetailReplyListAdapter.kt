package com.example.umc_wireframe.presentation.community.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvReplyBinding
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.presentation.community.detail.ReplyItem

class PostDetailReplyListAdapter :
    ListAdapter<CommentResultEntity.CommentEntity, PostDetailReplyListAdapter.ViewHolder>(
        PostDetailReplyDiffUtil()
    ) {
    inner class ViewHolder(
        val binding: ItemRvReplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentResultEntity.CommentEntity) = with(binding) {
            tvItemReplyWriter.text = item.memberNickname
            tvItemReplyContent.text = item.content

            if (item.reportCount >= 1) tvItemReplyBlur.visibility = View.VISIBLE
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

class PostDetailReplyDiffUtil : DiffUtil.ItemCallback<CommentResultEntity.CommentEntity>() {
    override fun areItemsTheSame(
        oldItem: CommentResultEntity.CommentEntity,
        newItem: CommentResultEntity.CommentEntity
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: CommentResultEntity.CommentEntity,
        newItem: CommentResultEntity.CommentEntity
    ): Boolean {
        return oldItem == newItem
    }

}
