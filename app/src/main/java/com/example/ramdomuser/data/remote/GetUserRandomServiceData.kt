package com.example.ramdomuser.data.remote

import com.example.ramdomuser.data.models.ResultModelData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserRandomServiceData @Inject constructor(
    private val retrofit:ApiInterface
) {

    suspend fun getUserRandomService(): List<ResultModelData>{
        return withContext(Dispatchers.IO){
            val response = retrofit.getUserRandom()
            return@withContext response.body()?.results ?: emptyList()
        }
    }
}