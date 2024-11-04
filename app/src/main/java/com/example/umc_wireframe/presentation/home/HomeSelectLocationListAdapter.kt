package com.example.umc_wireframe.presentation.home

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_wireframe.databinding.ItemRvSelectlocationBinding
import com.example.umc_wireframe.domain.model.ShortTermRegionObject

class HomeSelectLocationListAdapter(
    val clickListener: () -> Unit
) : ListAdapter<ShortTermRegionObject, HomeSelectLocationListAdapter.ViewHolder>(
    SelectLocationDiffUtil()
) {
    class ViewHolder(
        private val binding: ItemRvSelectlocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShortTermRegionObject) = with(binding) {
            btnItemRvLocal.text = item.region
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvSelectlocationBinding.inflate(
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

class SelectLocationDiffUtil : DiffUtil.ItemCallback<ShortTermRegionObject>() {
    override fun areItemsTheSame(
        oldItem: ShortTermRegionObject,
        newItem: ShortTermRegionObject
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ShortTermRegionObject,
        newItem: ShortTermRegionObject
    ): Boolean {
        return oldItem.region == newItem.region
    }

}