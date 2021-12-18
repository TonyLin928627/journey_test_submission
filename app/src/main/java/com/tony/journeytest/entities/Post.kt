package com.tony.journeytest.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts", indices = [Index(value = ["id", "title", "body"])])
data class Post(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(index = true)
    val userId: Int,

    @ColumnInfo(index = true)
    val title: String,

    @ColumnInfo(index = true)
    val body: String) : Parcelable
