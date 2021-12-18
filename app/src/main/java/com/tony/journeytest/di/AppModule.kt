package com.tony.journeytest.di

import android.content.Context
import com.tony.journeytest.BuildConfig
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
    fun provideRestApiService( jsonPlaceholderApi: IJsonPlaceholderApi): IJsonPlaceholderApi {
        return ApiServiceBuilder.getApiService(BuildConfig.API_BASE_URI)
    }
}