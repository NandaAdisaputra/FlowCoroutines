package com.nandaadisaputra.retrofit.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    @Volatile
    private var INSTANCE: BodyDatabase? = null

    @Synchronized
    fun getInstance(context: Context): BodyDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        val instance = Room.databaseBuilder(
            context.applicationContext,
            BodyDatabase::class.java,
            "Body_Database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        INSTANCE = instance
        return instance
    }

}