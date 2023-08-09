package com.wsl.domain.city.usecases

import com.wsl.data.city.CityRepository
import com.wsl.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FakeSearchCityByNameUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: CityRepository

    private lateinit var useCase: SearchCityByNameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SearchCityByNameUseCase(repository)
    }

    @Test
    fun `when do not find a city then responses success with empty list`() = runBlocking {
        //Given
        coEvery {
            repository.searchCityByName("GDL")
        } returns Result.Success(emptyList())

        //When
        val response = useCase.invoke(
            SearchCityByNameUseCase.Params(
                "GDL"
            )
        )

        //Then
        coVerify(exactly = 1) { repository.searchCityByName("GDL") }

        assert(response.isSuccess)
    }


}