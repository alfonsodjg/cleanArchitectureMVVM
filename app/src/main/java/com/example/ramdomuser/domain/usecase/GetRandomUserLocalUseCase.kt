package com.example.ramdomuser.domain.usecase

import com.example.ramdomuser.data.local.GetUserProvider
import com.example.ramdomuser.data.models.ResultModelData
import com.example.ramdomuser.data.repository.GetUserRepositoryData
import com.example.ramdomuser.domain.models.ResulModelDomain
import javax.inject.Inject

class GetRandomUserLocalUseCase @Inject constructor(
    private val repository: GetUserRepositoryData
){
    operator fun invoke(): ResultModelData? {
        val user = GetUserProvider.userRandomProvides
        if (user.isNotEmpty()){
            return user[0]
            }
        return null
        }
    }