import com.example.ramdomuser.data.local.GetUserProvider
import com.example.ramdomuser.data.repository.GetUserRepositoryData
import com.example.ramdomuser.domain.usecase.GetRandomUserLocalUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetRandomUserLocalUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: GetUserRepositoryData

    private lateinit var getRandomUserLocalUseCase: GetRandomUserLocalUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getRandomUserLocalUseCase = GetRandomUserLocalUseCase(repository)
    }

    @Test
    fun `when repository provides local user then return user`() = runBlocking {
        // Given
        val expectedUser = GetUserProvider.userRandomProvides.firstOrNull()
        coEvery { repository.getUserRandonDomainRepository() } returns emptyList()

        // When
        val result = getRandomUserLocalUseCase()

        // Then
        assertEquals(expectedUser, result)
    }

    @Test
    fun `when repository is empty then return null`() = runBlocking {
        // Given
        coEvery { repository.getUserRandonDomainRepository() } returns emptyList()

        // When
        val result = getRandomUserLocalUseCase()

        // Then
        assertEquals(null, result)
    }
}
