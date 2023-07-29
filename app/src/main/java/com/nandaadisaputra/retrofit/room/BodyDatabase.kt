package com.nandaadisaputra.retrofit.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nandaadisaputra.retrofit.room.dao.BodyDao
import com.nandaadisaputra.retrofit.room.entity.Body

@Database(
    entities = [Body::class],
    version = 1
)
abstract class BodyDatabase : RoomDatabase() {
    abstract fun bodyDao(): BodyDao

}