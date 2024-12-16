package com.example.umc_wireframe.presentation.community.communtiy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvPostBinding
import com.example.umc_wireframe.domain.model.entity.PostEntity

class CommunityListAdapter(
    val clickListener: (listId: String) -> Unit
) : PagingDataAdapter<PostEntity, CommunityListAdapter.ViewHolder>(
    CommunityListItemDiffUtil()
) {

    inner class ViewHolder(
        val binding: ItemRvPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostEntity) = with(binding) {
            root.setOnClickListener {
                clickListener(item.title)
            }

            tvItemCommentTitle.text = item.title
            tvItemCommentContent.text = item.content
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRvPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}

class CommunityListItemDiffUtil : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(
        oldItem: PostEntity,
        newItem: PostEntity,
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: PostEntity,
        newItem: PostEntity,
    ): Boolean {
        return oldItem == newItem
    }
}