package com.example.learnretrofit

import retrofit2.http.GET
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.Call

interface MyApi {
    @GET("/comments")
    fun getComments(): Call<List<Comments>>

    @DELETE("comments/{commentId}")
    fun deleteComment(@Path("commentId") commentId: Int): Call<Void>

    @GET("/albums")
    fun getAlbums(): Call<List<Albums>>

    @DELETE("albums/{albumId}")
    fun deleteAlbum(@Path("albumId") albumId: Int): Call<Void>
}