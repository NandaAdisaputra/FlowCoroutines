package com.nandaadisaputra.retrofit.room.dao

import androidx.room.*
import com.nandaadisaputra.retrofit.room.entity.Body

@Dao
interface BodyDao {

    @Query("SELECT * FROM body")
    fun getAll(): List<Body>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(hobby: List<Body>)

    @Delete
    fun delete(body: Body)
}