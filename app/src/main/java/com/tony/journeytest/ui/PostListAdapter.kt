package com.tony.journeytest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tony.journeytest.R
import com.tony.journeytest.databinding.PostCardBinding
import com.tony.journeytest.entities.Post

class PostListAdapter
    : ListAdapter<Post, PostListAdapter.PostItemViewHolder>(PostItemDiffCallback()) {

    var onItemClicked: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val binding: PostCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.post_card,
            parent,
            false)

        return PostItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val postItem = getItem(position)

        holder.itemView.setOnClickListener{
            onItemClicked?.invoke(postItem)
        }

        holder.bind(postItem)
    }

    inner class PostItemViewHolder(
        private val binding: PostCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(postItem: Post) {
            binding.post = postItem
            binding.executePendingBindings()
        }
    }
}

private class PostItemDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

}