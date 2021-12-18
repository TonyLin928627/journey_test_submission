package com.tony.journeytest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tony.journeytest.db.daos.CommentDao
import com.tony.journeytest.db.daos.PostDao
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.entities.Post

// Database versions:
//  1 - Initial version: Two tables, Posts, Comments
//  2 - Add indices to posts and comments
@Database(
    entities = [
        Post::class,
        Comment::class
    ],
    version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}

object Migrations {
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_posts_title` ON `posts` (`title`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_posts_body` ON `posts` (`body`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_posts_id_title_body` ON `posts` (`id`,`title`,`body`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_comments_body` ON `comments` (`body`)")
        }
    }
    val ALL_MIGRATIONS = listOf<Migration>(
        MIGRATION_1_2
    )
}

