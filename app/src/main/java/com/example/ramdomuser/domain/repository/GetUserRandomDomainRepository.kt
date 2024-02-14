package com.example.ramdomuser.domain.repository

import com.example.ramdomuser.data.models.ResultModelData
import com.example.ramdomuser.domain.models.ResulModelDomain

interface GetUserRandomDomainRepository {
    suspend fun getUserRandonDomainRepository(): List<ResultModelData>
}