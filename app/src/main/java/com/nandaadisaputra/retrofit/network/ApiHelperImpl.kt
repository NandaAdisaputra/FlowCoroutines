package com.nandaadisaputra.retrofit.network

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getBody() = flow { emit(apiService.getBody()) }
}