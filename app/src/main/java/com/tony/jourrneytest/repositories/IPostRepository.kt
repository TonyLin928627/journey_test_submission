package com.tony.jourrneytest.repositories

import com.tony.jourrneytest.entities.Comment
import kotlinx.coroutines.flow.Flow
import com.tony.jourrneytest.entities.Post

interface IPostRepository {
    suspend fun downloadPostsAndComments()

    suspend fun downloadNewPosts()

    suspend fun downloadNewCommentsOfPost(post: Post)

    suspend fun getAllPosts(): Flow<List<Post>>

    suspend fun getPostsWithTitleOrBody(title: String, body: String): Flow<List<Post>>

    suspend fun getCommentsOfPost(post: Post): Flow<List<Comment>>

    suspend fun getCommentsOfPostWithTitleOrBody(post: Post, title: String, body: String): Flow<List<Comment>>


}