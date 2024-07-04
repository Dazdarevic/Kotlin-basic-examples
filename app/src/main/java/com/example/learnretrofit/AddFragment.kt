package com.example.learnretrofit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnretrofit.databinding.FragmentAddBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding // Pretpostavljajući da koristite ViewBinding
//    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
//    private lateinit var recyclerViewAlbums: RecyclerView
//
//    // Lista podataka koju ćete prikazati u RecyclerView-u
//    private val albumsList = mutableListOf<Albums>()
//    private var commentsList: MutableList<Comments> = mutableListOf()
//    private lateinit var commentsAdapter: CommentsAdapter
//    private lateinit var recyclerView3: RecyclerView
//
//    // Adapter za RecyclerView
//    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflater za inflatovanje layout-a fragmenta
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicijalizacija RecyclerView-a iz layout-a fragmenta
        //val recyclerView = binding.recyclerViewAlbums
        //recyclerViewAlbumsecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Kreiranje i postavljanje adaptera na RecyclerView
        //albumsAdapter = AlbumsAdapter(albumsList,
          //  onDeleteClickListener = { album -> deleteAlbum(album) },
            //onDeleteToastListener = { showToast("Album deleted") })
        //deletedrecyclerView.adapter = albumsAdapter

        // Dohvatanje podataka i ažuriranje adaptera
//        getAllAlbums()

        //recyclerView3 = binding.recyclerView
        //recyclerView3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //commentsAdapter = CommentsAdapter(commentsList,
          //  onDeleteClickListener = { comment -> deleteComment(comment) },
            //onDeleteToastListener = { showToast("Comment deleted") }
        //)
        //recyclerView3.adapter = commentsAdapter

        //getAllComments()
    }


}
