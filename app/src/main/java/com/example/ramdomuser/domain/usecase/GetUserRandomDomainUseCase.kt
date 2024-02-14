package com.example.ramdomuser.domain.usecase

import com.example.ramdomuser.data.repository.GetUserRepositoryData
import com.example.ramdomuser.data.models.ResultModelData
import javax.inject.Inject

class GetUserRandomDomainUseCase @Inject constructor(
    private var userRepository: GetUserRepositoryData
) {
    suspend operator fun invoke(): List<ResultModelData>{
        return userRepository.getUserRandonDomainRepository()
    }

}