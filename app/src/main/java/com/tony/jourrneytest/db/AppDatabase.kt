package com.tony.jourrneytest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tony.jourrneytest.db.daos.CommentDao
import com.tony.jourrneytest.db.daos.PostDao
import com.tony.jourrneytest.entities.Comment
import com.tony.jourrneytest.entities.Post

// Database versions:
//  1 - Initial version: Two tables, Posts, Comments

@Database(
    entities = [
        Post::class,
        Comment::class
    ],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}


