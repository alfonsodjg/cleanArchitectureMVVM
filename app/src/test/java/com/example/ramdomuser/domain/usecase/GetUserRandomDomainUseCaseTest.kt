package com.example.ramdomuser.domain.usecase

import com.example.ramdomuser.data.repository.GetUserRepositoryData
import com.example.ramdomuser.data.models.ResultModelData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserRandomDomainUseCaseTest {

    @RelaxedMockK
    private lateinit var userRepository: GetUserRepositoryData

    private lateinit var getUserRandomDomainUseCase: GetUserRandomDomainUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getUserRandomDomainUseCase = GetUserRandomDomainUseCase(userRepository)
    }

    @Test
    fun `when invoke GetUserRandomDomainUseCase then return user data`() = runBlocking {
        // Given
        val expectedUserData = listOf(mockk<ResultModelData>())
        coEvery { userRepository.getUserRandonDomainRepository() } returns expectedUserData

        // When
        val result = getUserRandomDomainUseCase()

        // Then
        assertEquals(expectedUserData, result)

        // Verify
        coVerify(exactly = 1) { userRepository.getUserRandonDomainRepository() }
    }

    @Test
    fun `when repository throws exception then return empty list`() = runBlocking {
        // Given
        coEvery { userRepository.getUserRandonDomainRepository() } throws RuntimeException("Some error occurred")

        // When
        val result = getUserRandomDomainUseCase()

        // Then
        assertEquals(emptyList<ResultModelData>(), result)

        // Verify
        coVerify(exactly = 1) { userRepository.getUserRandonDomainRepository() }
    }


}
