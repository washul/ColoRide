package com.wsl.data.route

import com.wsl.domain.model.Route
import com.wsl.domain.route.datasource.RouteDataSource
import com.wsl.utils.Failure
import com.wsl.utils.Response
import java.util.*

class RouteRepository(
    val localDataSource: RouteLocalDataSource,
    val remoteDataSource: RouteRemoteDataSource
): RouteDataSource {


    override suspend fun getRoutes(startDate: Date, endDate: Date):
            Response<Failure, List<Route>> {
        return remoteDataSource.getRoutes(startDate, endDate)
    }


}