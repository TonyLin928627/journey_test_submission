package com.tony.jourrneytest.restApi
import com.tony.jourrneytest.entities.Comment
import com.tony.jourrneytest.entities.Post
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IJsonPlaceholderApi {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("comments")
    suspend fun getCommentsByPostId(@Query("postId") postId: Int): List<Comment>
}