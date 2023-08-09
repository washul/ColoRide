package com.wsl.domain.route.datasource

import com.wsl.domain.model.Route
import com.wsl.utils.Failure
import com.wsl.utils.Result
import java.time.LocalDateTime

interface RouteDataSource {

    suspend fun getRoutes(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        cityDeparture: String,
        cityArrival: String
    ): Result<Failure, Map<Int, List<Route>>>

    suspend fun createRoute(
        route: Route
    ): Result<Failure, Unit>

}