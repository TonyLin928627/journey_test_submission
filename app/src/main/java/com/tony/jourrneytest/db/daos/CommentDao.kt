package com.tony.jourrneytest.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tony.jourrneytest.entities.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert
    fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER by id")
    fun getCommentsByPostId(postId: Int) : Flow<List<Comment>>

    @Query("SELECT count(*) FROM comments WHERE id = :id")
    fun checkIfCommentExists(id: Int): Int
}