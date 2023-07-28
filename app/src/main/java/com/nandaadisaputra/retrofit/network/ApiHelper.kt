package com.nandaadisaputra.retrofit.network

import com.nandaadisaputra.retrofit.model.PostModel
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getBody(): Flow<List<PostModel>>
}