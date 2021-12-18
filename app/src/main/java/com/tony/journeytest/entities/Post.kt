package com.tony.journeytest.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(index = true)
    val userId: Int,

    val title: String,

    val body: String) : Parcelable
