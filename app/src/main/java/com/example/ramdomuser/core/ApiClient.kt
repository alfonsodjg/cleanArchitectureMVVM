package com.example.ramdomuser.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit?=null
    private val URL="https://randomuser.me/"

    @JvmStatic
    val instance: Retrofit?
        get(){
            retrofit=Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
}