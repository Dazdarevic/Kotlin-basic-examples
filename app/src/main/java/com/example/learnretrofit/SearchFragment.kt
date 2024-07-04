package com.example.learnretrofit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnretrofit.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding // Pretpostavljajući da koristite ViewBinding
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private lateinit var recyclerViewAlbums: RecyclerView

    // Lista podataka koju ćete prikazati u RecyclerView-u
    private val albumsList = mutableListOf<Albums>()

    // Adapter za RecyclerView
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflater za inflatovanje layout-a fragmenta
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicijalizacija RecyclerView-a iz layout-a fragmenta
        val recyclerView = binding.recyclerViewAlbums
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Kreiranje i postavljanje adaptera na RecyclerView
        albumsAdapter = AlbumsAdapter(albumsList,
            onDeleteClickListener = { album -> deleteAlbum(album) },
            onDeleteToastListener = { showToast("Album deleted") })
        recyclerView.adapter = albumsAdapter

        // Dohvatanje podataka i ažuriranje adaptera
        getAllAlbums()
    }

    private fun getAllAlbums() {
        Log.d("MainActivity", "getAllAlbums() called")

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getAlbums().enqueue(object : Callback<List<Albums>> {
            override fun onResponse(call: Call<List<Albums>>, response: Response<List<Albums>>) {
                if (response.isSuccessful) {
                    val albums = response.body()
                    if (albums != null && albums.size >= 10) {
                        val firstTenAlbums = albums.subList(0, 10) // Uzmi samo prvih 10 albuma
                        albumsList.addAll(firstTenAlbums)
                        albumsAdapter.notifyDataSetChanged()
                    } else {
                        // Handle case when there are less than 10 albums
                        albums?.let {
                            albumsList.addAll(it)
                            albumsAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Albums>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun deleteAlbum(album: Albums) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.deleteAlbum(album.albumId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val position = albumsList.indexOf(album)
                    albumsList.remove(album)
                    albumsAdapter.deleteAlbum(position)
                } else {
                    showToast("Failed to delete comment")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun showToast(message: String) {
        // Funkcija za prikaz Toast poruke
    }
}
