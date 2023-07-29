package com.nandaadisaputra.retrofit.room

import com.nandaadisaputra.retrofit.room.entity.Body
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {

    fun getBody(): Flow<List<Body>>

    fun insertAll(hobby: List<Body>): Flow<Unit>

}