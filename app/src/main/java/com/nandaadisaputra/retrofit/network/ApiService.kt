package com.nandaadisaputra.retrofit.network

import com.nandaadisaputra.retrofit.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getBody(): List<PostModel>
}