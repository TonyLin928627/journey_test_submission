package com.tony.journeytest.restApi
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.entities.Post
import retrofit2.http.*

interface IJsonPlaceholderApi {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("comments")
    suspend fun getCommentsByPostId(@Query("postId") postId: Int): List<Comment>
}