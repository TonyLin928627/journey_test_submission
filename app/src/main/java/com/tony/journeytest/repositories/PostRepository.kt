package com.tony.journeytest.repositories

import android.content.Context
import android.content.SharedPreferences
import com.tony.journeytest.db.daos.CommentDao
import com.tony.journeytest.db.daos.PostDao
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.entities.Post
import com.tony.journeytest.restApi.IJsonPlaceholderApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: IJsonPlaceholderApi,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    context: Context)
: IPostRepository {

    private val sharedPreferences = context.getSharedPreferences(this::class.java.canonicalName, Context.MODE_PRIVATE)

    override suspend fun deletePostsAndComments(){
        postDao.delete()
        commentDao.delete()
    }

    override suspend fun downloadPostsAndComments(): Pair<Int, Int> {
        var postCount = 0
        apiService.getPosts().also { postCount = it.size }.forEach { post ->
            postDao.insertPost(post)
        }

        var commentCount = 0
        apiService.getComments().also { commentCount = it.size }.forEach { comment->
            commentDao.insertComment(comment = comment)
        }

        sharedPreferences.edit().putLong("LastDownloadTime", System.currentTimeMillis()).apply()
        return Pair(postCount, commentCount)
    }

    override suspend fun downloadNewPosts() {
        apiService.getPosts().forEach { post ->
            postDao.checkIfPostExists(post.id).takeIf { it == 0 }?.let {
                postDao.insertPost(post)
            }
        }
    }

    override suspend fun downloadNewCommentsOfPost(post: Post) {
        apiService.getCommentsByPostId(postId = post.id).forEach { comment->
            commentDao.checkIfCommentExists(comment.id).takeIf { it == 0 }?.let {
                commentDao.insertComment(comment = comment)
            }
        }
    }

    override fun getAllPosts(): Flow<List<Post>> {
        return postDao.getPosts()
    }

    override suspend fun getPostsWithTitleOrBody(title: String, body: String): Flow<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun getCommentsOfPost(post: Post): List<Comment> {
       return commentDao.getCommentsByPostId(postId = post.id)
    }

    override suspend fun getCommentsOfPostWithTitleOrBody(post: Post, title: String, body: String): Flow<List<Comment>> {
        TODO("Not yet implemented")
    }

    override fun isDownloadingNeeded(): Boolean {
        return (System.currentTimeMillis() - sharedPreferences.getLong("LastDownloadTime", 0)) > 60_000
    }
}