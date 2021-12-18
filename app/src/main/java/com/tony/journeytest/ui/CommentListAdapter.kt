package com.tony.journeytest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tony.journeytest.R
import com.tony.journeytest.databinding.CommentCardBinding
import com.tony.journeytest.entities.Comment

class CommentListAdapter: ListAdapter<Comment, CommentListAdapter.CommentItemViewHolder>(CommentItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val binding: CommentCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.comment_card,
            parent,
            false)

        return CommentItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val commentItem = getItem(position)

        holder.bind(commentItem)
    }

    inner class CommentItemViewHolder(
        private val binding: CommentCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentItem: Comment) {
            binding.comment = commentItem
            binding.executePendingBindings()
        }
    }
}

private class CommentItemDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

}