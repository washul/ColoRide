package com.wsl.domain.route.datasource

import com.wsl.domain.model.Route
import com.wsl.utils.Failure
import com.wsl.utils.Response
import java.time.LocalDateTime

interface RouteDataSource {

    suspend fun getRoutes(
        startDate: LocalDateTime, endDate: LocalDateTime,
        cityDeparture: String,
        cityArrival: String
    ): Response<Failure, Map<Int, List<Route>>>

}