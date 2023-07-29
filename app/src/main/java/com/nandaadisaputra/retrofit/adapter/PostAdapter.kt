package com.nandaadisaputra.retrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.retrofit.R
import com.nandaadisaputra.retrofit.databinding.PostItemLayoutBinding
import com.nandaadisaputra.retrofit.room.entity.Body

class PostAdapter : ListAdapter<Body, PostAdapter.ItemViewHolder>(DiffUtilCallback()) {

    private var onItemClick: ((Body) -> Unit)? = null

    fun setOnClickItem(onClickItem: (Body) -> Unit) {
        this.onItemClick = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<PostItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.post_item_layout,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { hobby ->
            holder.bind(hobby)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(hobby)
            }
        }
    }

    inner class ItemViewHolder(private val binding: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(body: Body) {
            binding.post = body
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallback<T : Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem === newItem
        }
    }
}