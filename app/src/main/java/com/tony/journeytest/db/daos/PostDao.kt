package com.tony.journeytest.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tony.journeytest.entities.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface  PostDao {
    @Insert
    fun insertPost(post: Post)

    @Query("SELECT * FROM posts ORDER by id")
    fun getPosts() : Flow<List<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: Int): Post

    @Query("SELECT count(*) FROM posts WHERE id = :id ")
    fun checkIfPostExists(id: Int): Int

    @Query("DELETE FROM posts")
    fun delete()
}