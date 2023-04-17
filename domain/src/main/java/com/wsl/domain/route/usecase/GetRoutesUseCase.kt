package com.wsl.domain.route.usecase

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.model.Route
import com.wsl.domain.route.datasource.RouteDataSource
import com.wsl.utils.Failure
import com.wsl.utils.Response
import java.util.Date

class GetRoutesUseCase(private val dataSource: RouteDataSource) :
    UseCaseWithParams<List<Route>, GetRoutesUseCase.Params> {

    data class Params(val startDate: Date, val endDate: Date)

    override suspend fun invoke(params: Params): Response<Failure, List<Route>> {
        return dataSource.getRoutes(params.startDate, params.endDate)
    }

}