package com.wsl.data.route

import com.wsl.domain.model.Route
import com.wsl.domain.route.datasource.RouteDataSource
import com.wsl.utils.Failure
import com.wsl.utils.Response
import java.time.LocalDateTime

class RouteRepository(
    val localDataSource: RouteLocalDataSource,
    private val remoteDataSource: RouteRemoteDataSource
) : RouteDataSource {


    override suspend fun getRoutes(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        cityDeparture: String,
        cityArrival: String
    ):
            Response<Failure, Map<Int, List<Route>>> {
        return remoteDataSource.getRoutes(startDate, endDate, cityDeparture, cityArrival)
    }


}