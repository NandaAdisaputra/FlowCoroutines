package com.nandaadisaputra.retrofit.room

import com.nandaadisaputra.retrofit.room.entity.Body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val bodyDatabase: BodyDatabase) : DatabaseHelper {

    override fun getBody(): Flow<List<Body>> = flow {
        emit(bodyDatabase.bodyDao().getAll())
    }

    override fun insertAll(body: List<Body>): Flow<Unit> = flow {
        bodyDatabase.bodyDao().insertAll(body)
        emit(Unit)
    }

}