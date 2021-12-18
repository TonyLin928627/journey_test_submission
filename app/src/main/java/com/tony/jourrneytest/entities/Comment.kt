package com.tony.jourrneytest.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(index = true)
    val postId: Int,

    val name: String,
    val email: String,
    val body: String)
