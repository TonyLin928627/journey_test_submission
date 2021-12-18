package com.tony.jourrneytest.repositories

import com.tony.jourrneytest.db.daos.CommentDao
import com.tony.jourrneytest.db.daos.PostDao
import com.tony.jourrneytest.entities.Comment
import com.tony.jourrneytest.entities.Post
import com.tony.jourrneytest.restApi.IJsonPlaceholderApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: IJsonPlaceholderApi,
    private val postDao: PostDao,
    private val commentDao: CommentDao)
: IPostRepository {

    override suspend fun downloadPostsAndComments() {
        apiService.getPosts().forEach { post ->
            postDao.insertPost(post)
        }

        apiService.getComments().forEach { comment->
            commentDao.insertComment(comment = comment)
        }
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

    override suspend fun getAllPosts(): Flow<List<Post>> {
        return postDao.getPosts()
    }

    override suspend fun getPostsWithTitleOrBody(title: String, body: String): Flow<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentsOfPost(post: Post): Flow<List<Comment>> {
       return commentDao.getCommentsByPostId(postId = post.id)
    }

    override suspend fun getCommentsOfPostWithTitleOrBody(post: Post, title: String, body: String): Flow<List<Comment>> {
        TODO("Not yet implemented")
    }

}