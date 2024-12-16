package com.example.umc_wireframe.presentation.community.communtiy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvPostBinding

class CommunityListAdapter(
    val clickListener: (listId: String) -> Unit
) : PagingDataAdapter<CommunityListItem, CommunityListAdapter.ViewHolder>(
        CommunityListItemDiffUtil()
    ) {

    inner class ViewHolder(
        val binding: ItemRvPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityListItem) = with(binding) {
            root.setOnClickListener {
                clickListener(item.id)
            }

            tvItemCommentWriter.text = item.writer
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

class CommunityListItemDiffUtil : DiffUtil.ItemCallback<CommunityListItem>() {
    override fun areItemsTheSame(
        oldItem: CommunityListItem,
        newItem: CommunityListItem,
    ): Boolean {
        return oldItem.content == newItem.content
    }

    override fun areContentsTheSame(
        oldItem: CommunityListItem,
        newItem: CommunityListItem,
    ): Boolean {
        return oldItem == newItem
    }
}