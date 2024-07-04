package com.example.learnretrofit

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class AlbumDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        // Dobijanje prenesenih podataka iz intenta
        val albumTitle = intent.getStringExtra("album_title")
        val albumImageUrl = intent.getStringExtra("album_image_url")

        // Postavljanje detalja albuma u UI
        val titleTextView: TextView = findViewById(R.id.albumDetailTitle)
        val imageView: ImageView = findViewById(R.id.albumDetailImage)

        titleTextView.text = albumTitle

        Glide.with(this)
            .load(albumImageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(imageView)
    }
}
