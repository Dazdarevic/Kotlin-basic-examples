package com.example.learnretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.learnretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val TAG: String = "CHECK_RESPONSE"
    private lateinit var recyclerViewAlbums: RecyclerView
    private  lateinit var albumsAdapter: AlbumsAdapter
    private var albumsList: MutableList<Albums> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private var commentsList: MutableList<Comments> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(AddFragment())

        // bottom navigacija
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {

                R.id.home -> replaceFragment(AddFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.add -> replaceFragment(AddFragment())
                R.id.settings -> replaceFragment(SettingsFragment())


                else -> {

                }
            }
            true
        }
//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        commentsAdapter = CommentsAdapter(commentsList,
//            onDeleteClickListener = { comment -> deleteComment(comment) },
//            onDeleteToastListener = { showToast("Comment deleted") }
//        )
//        recyclerView.adapter = commentsAdapter
//
//        getAllComments()
    }

    // metoda za promenu fragmenata
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    private fun getAllComments() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        commentsList.addAll(it)
                        commentsAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun deleteComment(comment: Comments) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.deleteComment(comment.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val position = commentsList.indexOf(comment)
                    commentsList.remove(comment)
                    commentsAdapter.deleteItem(position)
                } else {
                    showToast("Failed to delete comment")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                showToast("Failed to delete comment")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
