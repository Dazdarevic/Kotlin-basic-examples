package com.example.learnretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AlbumCommentAdapter(
    private val comments: MutableList<Comments>,
    private val albums: MutableList<Albums>,
    private val onDeleteAlbumClickListener: (Albums) -> Unit,
    private val onDeleteCommentClickListener: (Comments) -> Unit,
    private val onDeleteToastListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val albumViewType = 1
    private val commentViewType = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            albumViewType -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
                AlbumViewHolder(view)
            }
            commentViewType -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
                CommentViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            albumViewType -> {
                val albumHolder = holder as AlbumViewHolder
                val album = albums[position]
                albumHolder.bind(album)
            }
            commentViewType -> {
                val commentHolder = holder as CommentViewHolder
                val comment = comments[position - albums.size] // Adjust position for comments list
                commentHolder.bind(comment)
            }
        }
    }

    override fun getItemCount(): Int {
        return albums.size + comments.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < albums.size) {
            albumViewType
        } else {
            commentViewType
        }
    }

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumTitle: TextView = itemView.findViewById(R.id.albumTitle)
        private val albumImage: ImageView = itemView.findViewById(R.id.albumImage)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDeleteAlbum)

        fun bind(album: Albums) {
            albumTitle.text = album.title
            // Load image using Glide
            Glide.with(itemView.context)
                .load(album.url)
                .override(200, 100)
                .placeholder(R.drawable.ic_launcher_background) // placeholder image
                .error(R.drawable.ic_launcher_foreground) // error image
                .into(albumImage)

            itemView.setOnClickListener {
                onDeleteAlbumClickListener(album)
            }

            btnDelete.setOnClickListener {
                onDeleteAlbumClickListener(album)
            }
        }
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentTextView: TextView = itemView.findViewById(R.id.commentTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(comment: Comments) {
            commentTextView.text = comment.body

            deleteButton.setOnClickListener {
                onDeleteCommentClickListener(comment)
            }
        }
    }

    fun deleteAlbum(position: Int) {
        albums.removeAt(position)
        notifyItemRemoved(position)
        onDeleteToastListener()
    }

    fun deleteComment(position: Int) {
        comments.removeAt(position - albums.size) // Adjust position for comments list
        notifyItemRemoved(position)
        onDeleteToastListener()
    }
}
