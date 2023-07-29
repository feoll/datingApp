package com.example.datingapp.di.modules

import android.content.Context
import com.example.datingapp.data.api.DeckApi
import com.example.datingapp.data.common.BASE_URL
import com.example.datingapp.data.repositories.FakeSwipeRepository
import com.example.datingapp.data.repositories.ProfileRepository
import com.example.datingapp.data.repositories.ProfileRepositoryImpl
import com.example.datingapp.data.repositories.SwipeRepository
import com.example.datingapp.data.repositories.SwipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideDeckApi(): DeckApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeckApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSwipeRepository(
        @ApplicationContext context: Context,
        api: DeckApi
    ): SwipeRepository {
        return SwipeRepositoryImpl(context = context, api = api)
    }

//    @Provides
//    @Singleton
//    fun provideSwipeRepository(): SwipeRepository {
//        return FakeSwipeRepository()
//    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        @ApplicationContext context: Context,
        api: DeckApi
    ): ProfileRepository {
        return ProfileRepositoryImpl(context = context, api = api)
    }

}