package com.example.ramdomuser.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ramdomuser.data.models.LocationModelData
import com.example.ramdomuser.data.models.LoginModelData
import com.example.ramdomuser.data.models.NameModelData
import com.example.ramdomuser.data.models.PictureModelData
import com.example.ramdomuser.data.models.RegisteredModelData
import com.example.ramdomuser.data.models.ResultModelData
import com.example.ramdomuser.domain.usecase.GetRandomUserLocalUseCase
import com.example.ramdomuser.domain.usecase.GetUserRandomDomainUseCase
import com.example.ramdomuser.ui.models.ResultView
import com.example.ramdomuser.ui.viewstate.EmailViewState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EmailViewModelTest {

    @RelaxedMockK
    private lateinit var userCase: GetUserRandomDomainUseCase

    @RelaxedMockK
    private lateinit var useCaseUserProvider: GetRandomUserLocalUseCase

    private lateinit var emailViewModel: EmailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        emailViewModel = EmailViewModel(userCase, useCaseUserProvider)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all data and set the first value`() = runTest {
        // Given
        val resultModelList = listOf(
            ResultModelData(
                email = "angelalbertoeslava@gmail.com",
                login = LoginModelData("Qwerty"),
                picture = PictureModelData("large-url", "medium-url", "thumbnail-url"),
                location = LocationModelData("xddd", "xddd", " ", ""),
                name= NameModelData("", "", ""),
                phone = "9211556476",
                registered = RegisteredModelData(0, "",)
            )
        )
        coEvery { userCase() } returns resultModelList

        // When
        emailViewModel.onNewUser()

        // Then
        val expectedViewState = EmailViewState(
            isLoading = false,
            userRandom = listOf(
                ResultView(
                    email = "angelalbertoeslava@gmail.com",
                    login = LoginModelData("Qwerty"),
                    picture = PictureModelData("large-url", "medium-url", "thumbnail-url")
                )
            )
        )
        assertEquals(expectedViewState, emailViewModel.viewState.value)
    }

    @Test
    fun `when onUserProvider is called, update viewState with local user data`() = runTest {
        // Given
        val localUser = ResultModelData(
            email = "localuser@example.com",
            login = LoginModelData("LocalUser123"),
            picture = PictureModelData("local-large", "local-medium", "local-thumbnail"),
            location = LocationModelData("local-x", "local-y", "", ""),
            name = NameModelData("Local", "User", ""),
            phone = "1234567890",
            registered = RegisteredModelData(0, "")
        )
        coEvery { useCaseUserProvider() } returns localUser

        // When
        emailViewModel.onUserProvider()

        // Then
        val expectedViewState = EmailViewState(
            isLoading = false,
            userRandom = listOf(
                ResultView(
                    email = "localuser@example.com",
                    login = LoginModelData("LocalUser123"),
                    picture = PictureModelData("local-large", "local-medium", "local-thumbnail")
                )
            )
        )
        assertEquals(expectedViewState, emailViewModel.viewState.value)
    }

//    @Test
//    fun `when viewmodel is created at the first time and domain use case returns empty list, set default value`() = runTest {
//        // Given
//        coEvery { userCase() } returns emptyList()
//
//        // When
//        emailViewModel.onNewUser()
//
//        // Then
//        val expectedViewState = EmailViewState(isLoading = false, userRandom = emptyList())
//        assertEquals(expectedViewState, emailViewModel.viewState.value)
//    }
//
//    @Test
//    fun `when onUserProvider is called and local use case returns null, set default value`() = runTest {
//        // Given
//        coEvery { useCaseUserProvider() } returns null
//
//        // When
//        emailViewModel.onUserProvider()
//
//        // Then
//        val expectedViewState = EmailViewState(isLoading = false, userRandom = emptyList())
//        assertEquals(expectedViewState, emailViewModel.viewState.value)
//    }

}
