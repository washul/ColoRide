package com.wsl.domain.route.usecase

import com.wsl.data.route.COLOTLAN_CITY
import com.wsl.data.route.GDL_CITY
import com.wsl.data.route.RouteRepository
import com.wsl.utils.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime


internal class FakeGetRoutesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: RouteRepository

    private lateinit var useCase: GetRoutesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetRoutesUseCase(repository)
    }

    @Test
    fun `when api return 0 returns success`() = runBlocking {
        //Given
        coEvery {
            repository.getRoutes(any(), any(), any(), any())
        } returns Result.Success(mapOf())

        //When
        val response = useCase.invoke(
            GetRoutesUseCase.Params(
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusDays(7),
                cityDeparture = COLOTLAN_CITY.name,
                cityArrival = GDL_CITY.name
            )
        )

        //Then
        coVerify(exactly = 1) {
            repository.getRoutes(any(), any(), any(), any())
        }

        assert(response.isSuccess)
    }

}