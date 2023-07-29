package com.nandaadisaputra.retrofit.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*bungkus dengan interface Parcelable untuk Implementasi Intent mengirim Data dengan Parcelable."*/
@Parcelize
@Entity(tableName = "body")
data class Body(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "body")
    val body:String
) : Parcelable