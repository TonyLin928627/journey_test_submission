package com.tony.journeytest.repositories

import com.tony.journeytest.entities.Comment
import kotlinx.coroutines.flow.Flow
import com.tony.journeytest.entities.Post

interface IPostRepository {
    suspend fun deletePostsAndComments()

    suspend fun downloadPostsAndComments(): Pair<Int, Int>

    suspend fun downloadNewPosts()

    suspend fun downloadNewCommentsOfPost(post: Post)

    fun getAllPosts(): Flow<List<Post>>

    fun getPostsWithSearchKey(searchKey: String): Flow<List<Post>>

    fun getCommentsOfPost(post: Post): List<Comment>

    fun isDownloadingNeeded(): Boolean
}