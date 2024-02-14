package com.example.ramdomuser.data.remote

import com.example.ramdomuser.data.models.UserRandomData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/api")
    suspend fun getUserRandom(): Response<UserRandomData>
}