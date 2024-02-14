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
import com.example.ramdomuser.ui.models.RegisteredView
import com.example.ramdomuser.ui.viewstate.BirthdayViewState
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
class BirthdayViewModelTest{
    @RelaxedMockK
    private lateinit var userCase: GetUserRandomDomainUseCase

    @RelaxedMockK
    private lateinit var useCaseUserProvider: GetRandomUserLocalUseCase

    private lateinit var birthdayViewModel: BirthdayViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        birthdayViewModel = BirthdayViewModel(userCase, useCaseUserProvider)
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
                email = "any email",
                login = LoginModelData("any login"),
                picture = PictureModelData("any large-url", "any medium-url", "any thumbnail-url"),
                location = LocationModelData("any city", "any state", "any country", "any postcode"),
                name = NameModelData("any first", "any last", "any title"),
                phone = "any phone",
                registered = RegisteredModelData(25, "any date")
            )
        )
        coEvery { userCase() } returns resultModelList

        // When
        birthdayViewModel.onNewUser()

        // Then
        val expectedViewState = BirthdayViewState(
            isLoading = false,
            userRandom = listOf(
                RegisteredView(
                    date = "any date",
                    location = LocationModelData("any city", "any state", "any country", "any postcode"),
                    picture = PictureModelData("any large-url", "any medium-url", "any thumbnail-url")
                )
            )
        )
        assertEquals(expectedViewState, birthdayViewModel.viewState.value)
    }

    @Test
    fun `when onUserProvider is called, update viewState with local user data`() = runTest {
        // Given
        val localUser = ResultModelData(
            email = "any email",
            login = LoginModelData("any login"),
            picture = PictureModelData("any large-url", "any medium-url", "any thumbnail-url"),
            location = LocationModelData("any city", "any state", "any country", "any postcode"),
            name = NameModelData("any first", "any last", "any title"),
            phone = "any phone",
            registered = RegisteredModelData(25, "any date")
        )
        coEvery { useCaseUserProvider() } returns localUser

        // When
        birthdayViewModel.userProvider()

        // Then
        val expectedViewState = BirthdayViewState(
            isLoading = false,
            userRandom = listOf(
                RegisteredView(
                    date = "any date",
                    location = LocationModelData("any city", "any state", "any country", "any postcode"),
                    picture = PictureModelData("any large-url", "any medium-url", "any thumbnail-url")
                )
            )
        )
        assertEquals(expectedViewState, birthdayViewModel.viewState.value)
    }
}