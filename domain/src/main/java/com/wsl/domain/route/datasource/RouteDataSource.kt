package com.wsl.domain.route.datasource

import com.wsl.domain.model.Route
import com.wsl.utils.Failure
import com.wsl.utils.Response
import java.util.Date

interface RouteDataSource {

    suspend fun getRoutes(startDate: Date, endDate: Date): Response<Failure, List<Route>>

}