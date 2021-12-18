package com.tony.journeytest.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tony.journeytest.entities.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert
    fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER by id")
    fun getCommentsByPostId(postId: Int) : List<Comment>

    @Query("SELECT count(*) FROM comments WHERE id = :id")
    fun checkIfCommentExists(id: Int): Int

    @Query("DELETE FROM comments")
    fun delete()
}