package com.tony.journeytest.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.tony.journeytest.db.daos.CommentDao
import com.tony.journeytest.db.daos.PostDao
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.entities.Post
import com.tony.journeytest.restApi.IJsonPlaceholderApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: IJsonPlaceholderApi,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    context: Context)
: IPostRepository {

    companion object {
        private const val LastDownloadTime = "LastDownloadTime"
        private const val DownloadInterval = 60_000 //one minute
    }
    private val sharedPreferences = context.getSharedPreferences(this::class.java.canonicalName, Context.MODE_PRIVATE)

    /**
     * clean the database by deleting all existing posts and comments
     */
    override suspend fun deletePostsAndComments(){
        postDao.delete()
        commentDao.delete()
    }

    /**
     * download posts and comments from endponts and save to local database
     */
    override suspend fun downloadPostsAndComments(): Pair<Int, Int> {
        //1. download and save posts
        var postCount = 0
        apiService.getPosts().also { postCount = it.size }.forEach { post ->
            postDao.insertPost(post)
        }

        //2. download and save comments
        var commentCount = 0
        apiService.getComments().also { commentCount = it.size }.forEach { comment->
            commentDao.insertComment(comment = comment)
        }

        //3. save the time when this download/save iss done
        sharedPreferences.edit().putLong(LastDownloadTime, System.currentTimeMillis()).apply()
        return Pair(postCount, commentCount)
    }

    /**
     * query all saved posts from database
     */
    override fun getAllPosts(): Flow<List<Post>> {
        return postDao.getPosts()
    }

    /**
     * query posts with the given search key.
     * The posts that contain "search key" in their title or body,
     * or have comments that contain the "search key" will be returned
     */
    override fun getPostsWithSearchKey(searchKey: String): Flow<List<Post>>{
        return flow {
            val postIds = commentDao.getPostIdsWithSearchKey(searchKey.toSearchKey())
            val posts = postDao.getPostIdsWithSearchKey(searchKey.toSearchKey(), postIds)

            Log.d("searchKey", "$searchKey -> ${posts.size}")
            emit(posts)
        }
    }

    /**
     * query all the comments belong to the given post
     */
    override fun getCommentsOfPost(post: Post): List<Comment> {
       return commentDao.getCommentsByPostId(postId = post.id)
    }

    /**
     * query the comments that their body contain the search key
     */
    override suspend fun getCommentsOfPostWithSearchKey(post: Post, searchKey: String): List<Comment>{
        return commentDao.getCommentsWithSearchKey(postId = post.id, searchKey = searchKey.toSearchKey())
    }

    /**
     * check weather or not should downland and save posts and comments
     * return true if the interval between present and last download/save is greater than the value of const DownloadInterval
     */
    override fun isDownloadingNeeded(): Boolean {
        return (System.currentTimeMillis() - sharedPreferences.getLong(LastDownloadTime, Long.MAX_VALUE)) > DownloadInterval
    }
}

private fun String.toSearchKey(): String {
    return "%$this%"
}
