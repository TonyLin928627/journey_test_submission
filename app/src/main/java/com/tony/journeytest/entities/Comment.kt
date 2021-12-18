package com.tony.journeytest.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(index = true)
    val postId: Int,

    val name: String,
    val email: String,

    @ColumnInfo(index = true)
    val body: String)
