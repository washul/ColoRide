package com.wsl.domain.city.usecases

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.city.datasource.CityDataSource
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.Failure
import com.wsl.utils.Result

class SetCityPreferenceUseCase(private val dataSource: CityDataSource) :
    UseCaseWithParams<Unit, SetCityPreferenceUseCase.Params> {

    data class Params(val city: City, val placeOfTheRoute: PlaceOfTheRoute)

    override suspend fun invoke(params: Params): Result<Failure, Unit> =
        dataSource.setCityDepartureToPreferences(
            city = params.city,
            placeOfTheRoute = params.placeOfTheRoute
        )


}