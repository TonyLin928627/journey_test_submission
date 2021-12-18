package com.tony.journeytest.repositories

import com.tony.journeytest.entities.Comment
import kotlinx.coroutines.flow.Flow
import com.tony.journeytest.entities.Post

interface IPostRepository {
    suspend fun downloadPostsAndComments(): Pair<Int, Int>

    suspend fun downloadNewPosts()

    suspend fun downloadNewCommentsOfPost(post: Post)

    fun getAllPosts(): Flow<List<Post>>

    suspend fun getPostsWithTitleOrBody(title: String, body: String): Flow<List<Post>>

    fun getCommentsOfPost(post: Post): List<Comment>

    suspend fun getCommentsOfPostWithTitleOrBody(post: Post, title: String, body: String): Flow<List<Comment>>

    suspend fun deletePostsAndComments()

    fun isDownloadingNeeded(): Boolean
}