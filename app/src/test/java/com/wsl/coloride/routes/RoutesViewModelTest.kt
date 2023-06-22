package com.wsl.coloride.routes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wsl.coloride.screens.routes.RoutesViewModel
import com.wsl.domain.model.Route
import com.wsl.domain.route.usecase.GetRoutesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import com.wsl.utils.Result
import io.mockk.coVerify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RoutesViewModelTest {

    private lateinit var viewModel: RoutesViewModel

    @RelaxedMockK
    private lateinit var getRoutesUseCase: GetRoutesUseCase

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is created at the first time, call to fetch routes`() = runTest {
        //Given
        val value: Map<Int, List<Route>> = mapOf(0 to listOf())
        val response: Result.Success<Map<Int, List<Route>>> = Result.Success( value )
        coEvery { getRoutesUseCase(any()) } returns response

        //When
        viewModel = RoutesViewModel(getRoutesUseCase)

        //Then
        coVerify(exactly = 1) { getRoutesUseCase(any()) }
        assert(viewModel.routesList.value == value)
    }

    @Test
    fun `when viewModel GetRoutesUseCase return empty, keep the last value`() = runTest {
        //Given
        val value: Map<Int, List<Route>> = mapOf(0 to listOf(), 1 to listOf<Route>())
        val response: Result.Success<Map<Int, List<Route>>> = Result.Success( value )
        coEvery { getRoutesUseCase(any()) } returns response
        viewModel = RoutesViewModel(getRoutesUseCase)


        val valueEmpty: Map<Int, List<Route>> = emptyMap()
        val responseEmpty = Result.Success(valueEmpty)

        coEvery { getRoutesUseCase(any()) } returns responseEmpty

        //When

        viewModel.fetch()

        //Then
        coVerify(exactly = 2) { getRoutesUseCase(any()) }
        assert(viewModel.routesList.value == value)
    }
}
