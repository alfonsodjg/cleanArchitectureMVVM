package com.example.ramdomuser.core.di

import com.example.ramdomuser.core.ApiClient
import com.example.ramdomuser.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClientProvider {
    private const val URL="https://randomuser.me/"

    @Singleton
    @Provides
    fun retrofitProvides(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun serviceProvides(retrofit: Retrofit): ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}