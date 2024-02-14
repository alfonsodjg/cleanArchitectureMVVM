package com.example.ramdomuser.data.repository

import com.example.ramdomuser.data.local.GetUserProvider
import com.example.ramdomuser.data.models.ResultModelData
import com.example.ramdomuser.data.remote.GetUserRandomServiceData
import com.example.ramdomuser.domain.models.ResulModelDomain
import com.example.ramdomuser.domain.repository.GetUserRandomDomainRepository
import javax.inject.Inject

class GetUserRepositoryData @Inject constructor(
    private var userService:GetUserRandomServiceData
): GetUserRandomDomainRepository {
    override suspend fun getUserRandonDomainRepository(): List<ResultModelData> {
        val response = userService.getUserRandomService()
        GetUserProvider.userRandomProvides = response
        return response
    }
}