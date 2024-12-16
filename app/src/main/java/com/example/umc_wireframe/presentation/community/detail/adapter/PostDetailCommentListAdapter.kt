package com.example.umc_wireframe.presentation.community.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvCommentBinding
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.presentation.community.detail.PostDetailCommentItem

class PostDetailCommentListAdapter(
    val postReply: (parentId:Int) -> Unit
) :
    PagingDataAdapter<CommentResultEntity.CommentEntity, PostDetailCommentListAdapter.ViewHolder>(
        PostDetailCommentDiffUtil()
    ) {

    inner class ViewHolder(
        val binding: ItemRvCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommentResultEntity.CommentEntity) = with(binding) {
            tvItemCommentTitle.text = item.memberNickname
            tvItemCommentContent.text = item.content

            val listAdapter = PostDetailReplyListAdapter()

            if (item.reportCount >= 1) tvItemReplyBlur.visibility = View.VISIBLE
            else tvItemReplyBlur.visibility = View.GONE

            tvItemReplyBlur.setOnClickListener {
                tvItemReplyBlur.visibility = View.GONE
            }

            rvItemCommentReply.run {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = listAdapter
                listAdapter.submitList(item.children)
            }

            tvItemCommentReplyAdd.setOnClickListener {
                rvItemCommentReply.visibility = View.VISIBLE
            }

            tvItemCommentReplyPost.setOnClickListener {
                postReply(item.id)
            }

            tvItemCommentReplyAdd.text = "대댓글 보기(${item.children.size})"
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

class PostDetailCommentDiffUtil : DiffUtil.ItemCallback<CommentResultEntity.CommentEntity>() {
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
