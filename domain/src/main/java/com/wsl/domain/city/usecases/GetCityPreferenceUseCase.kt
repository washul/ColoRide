package com.wsl.domain.city.usecases

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.city.datasource.CityDataSource
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.Failure
import com.wsl.utils.Result

class GetCityPreferenceUseCase(private val dataSource: CityDataSource) : UseCaseWithParams<City, GetCityPreferenceUseCase.Params> {

    data class Params(val placeOfTheRoute: PlaceOfTheRoute)

    override suspend fun invoke(params: Params): Result<Failure, City> {
        val city = dataSource.getCityFromPreferences(params.placeOfTheRoute)
        return if (city == null)
            Result.Failure(Failure.CustomError("Do not set City departure on preferences"))
        else Result.Success(
            city
        )
    }

    //If user do no have city as departure preference, send a failure


}