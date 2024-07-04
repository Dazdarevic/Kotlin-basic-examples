package com.example.learnretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentsAdapter(
    private val comments: MutableList<Comments>,
    private val onDeleteClickListener: (Comments) -> Unit,
    private val onDeleteToastListener: () -> Unit
) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTextView: TextView = itemView.findViewById(R.id.commentTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener(comments[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.commentTextView.text = comment.body
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun deleteItem(position: Int) {
        comments.removeAt(position)
        notifyItemRemoved(position)
        onDeleteToastListener()
    }
}
