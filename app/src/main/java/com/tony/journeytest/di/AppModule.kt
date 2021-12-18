package com.tony.journeytest.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tony.journeytest.BuildConfig
import com.tony.journeytest.db.AppDatabase
import com.tony.journeytest.db.Migrations
import com.tony.journeytest.db.daos.CommentDao
import com.tony.journeytest.db.daos.PostDao
import com.tony.journeytest.repositories.IPostRepository
import com.tony.journeytest.repositories.PostRepository
import com.tony.journeytest.restApi.ApiServiceBuilder
import com.tony.journeytest.restApi.IJsonPlaceholderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideRestApiService(): IJsonPlaceholderApi {
        return ApiServiceBuilder.getApiService(BuildConfig.API_BASE_URI)
    }

    @Provides
    @Singleton
    fun providePostRepository( apiService: IJsonPlaceholderApi, postDao: PostDao, commentDao: CommentDao, context: Context): IPostRepository {
        return PostRepository(
            apiService = apiService,
            postDao = postDao,
            commentDao = commentDao,
            context = context
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        val builder = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "journeytest.db")
            .addMigrations(*Migrations.ALL_MIGRATIONS.toTypedArray())

        // Disable Write Ahead Logging for debug so we can easily copy the DB file from the device
        if (BuildConfig.DEBUG) {
            builder.setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
        }

        return builder.build()
    }

    @Provides
    fun providePostDao(database: AppDatabase): PostDao {
        return database.postDao()
    }

    @Provides
    fun provideCommentDao(database: AppDatabase): CommentDao {
        return database.commentDao()
    }
}