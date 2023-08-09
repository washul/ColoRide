package com.wsl.domain.city.usecases

import com.wsl.data.city.CityRepository
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.Failure
import com.wsl.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FakeGetCityPreferencesUseCaseTest {

    @RelaxedMockK
    lateinit var repository: CityRepository

    lateinit var useCase: GetCityPreferenceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetCityPreferenceUseCase(repository)
    }

    /**
     * Departure city from preferences
     * */


    @Test
    fun `when departure city from preferences is empty return failure`() = runBlocking {
        val placeOfTheRoute = PlaceOfTheRoute.Departure
        //Given
        coEvery {
            repository.getCityFromPreferences(placeOfTheRoute)
        } returns null

        //When
        val response = useCase.invoke(
            GetCityPreferenceUseCase.Params(
                placeOfTheRoute
            )
        )

        //Then
        coVerify(exactly = 1) {
            repository.getCityFromPreferences(placeOfTheRoute)
        }

        assert(response.isFailure)
    }

    @Test
    fun `when departure city from preferences is not empty return success`() = runBlocking {
        val placeOfTheRoute = PlaceOfTheRoute.Departure
        //Given
        coEvery {
            repository.getCityFromPreferences(placeOfTheRoute)
        } returns City("Guadalajara")

        //When
        val response = useCase.invoke(
            GetCityPreferenceUseCase.Params(
                placeOfTheRoute
            )
        )

        //Then
        coVerify(exactly = 1) {
            repository.getCityFromPreferences(placeOfTheRoute)
        }

        assert(response.isSuccess)
    }

    /**
     * Arrival city from preferences
     * */

    @Test
    fun `when arrival city from preferences is empty return failure`() = runBlocking {
        val placeOfTheRoute = PlaceOfTheRoute.Arrival
        //Given
        coEvery {
            repository.getCityFromPreferences(placeOfTheRoute)
        } returns null

        //When
        val response = useCase.invoke(
            GetCityPreferenceUseCase.Params(
                placeOfTheRoute
            )
        )

        //Then
        coVerify(exactly = 1) {
            repository.getCityFromPreferences(placeOfTheRoute)
        }

        assert(response.isFailure)
    }

    @Test
    fun `when arrival city from preferences is not empty return success`() = runBlocking {
        val placeOfTheRoute = PlaceOfTheRoute.Arrival
        //Given
        coEvery {
            repository.getCityFromPreferences(placeOfTheRoute)
        } returns City("Guadalajara")

        //When
        val response = useCase.invoke(
            GetCityPreferenceUseCase.Params(
                placeOfTheRoute
            )
        )

        //Then
        coVerify(exactly = 1) {
            repository.getCityFromPreferences(placeOfTheRoute)
        }

        assert(response.isSuccess)
    }
}