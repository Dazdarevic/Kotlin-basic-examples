package com.example.learnretrofit

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class AlbumsAdapter(
    private val albums: MutableList<Albums>,
    private val onDeleteClickListener: (Albums) -> Unit,
    private val onDeleteToastListener: () -> Unit
): RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumTitle: TextView = itemView.findViewById(R.id.albumTitle)
        val albumImage: ImageView = itemView.findViewById(R.id.albumImage)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteAlbum)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val album = albums[position]
                    // Pokreni AlbumDetailActivity i prenesi detalje o albumu
                    val intent = Intent(itemView.context, AlbumDetailActivity::class.java)
                    intent.putExtra("album_title", album.title)
                    intent.putExtra("album_image_url", album.url)
                    itemView.context.startActivity(intent)
                }
            }

            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener(albums[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.albumTitle.text = album.title
        // Učitajte sliku koristeći Glide
        Glide.with(holder.itemView.context)
            .load(album.url)
            .override(200, 100)
            .placeholder(R.drawable.ic_launcher_background) // placeholder slika
            .error(R.drawable.ic_launcher_foreground) // slika za grešku
            .into(holder.albumImage)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    fun deleteAlbum(position: Int) {
        albums.removeAt(position)
        notifyItemRemoved(position)
        onDeleteToastListener()
    }
}
