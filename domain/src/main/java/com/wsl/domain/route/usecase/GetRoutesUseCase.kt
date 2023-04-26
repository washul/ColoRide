package com.wsl.domain.route.usecase

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.model.Route
import com.wsl.domain.route.datasource.RouteDataSource
import com.wsl.utils.Failure
import com.wsl.utils.Response
import com.wsl.utils.map
import java.time.LocalDateTime

class GetRoutesUseCase(private val dataSource: RouteDataSource) :
    UseCaseWithParams<Map<Int, List<Route>>, GetRoutesUseCase.Params> {

    data class Params(
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val cityDeparture: String,
        val cityArrival: String
    )

    override suspend fun invoke(params: Params): Response<Failure, Map<Int, List<Route>>> {
        return dataSource.getRoutes(
            params.startDate,
            params.endDate,
            params.cityDeparture,
            params.cityArrival
        )
    }

}