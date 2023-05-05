package com.wsl.data.city

import com.wsl.domain.city.datasource.CityDataSource
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.Failure
import com.wsl.utils.Result

class CityRepository(
    private val localDataSource: CityLocalDataSource,
    private val remoteDataSource: CityRemoteDataSource
) : CityDataSource {

    override suspend fun getCityFromPreferences(placeOfTheRoute: PlaceOfTheRoute): City? {
        return localDataSource.getCityFromPreferences(placeOfTheRoute)
    }

    override suspend fun setCityDepartureToPreferences(
        city: City,
        placeOfTheRoute: PlaceOfTheRoute
    ): Result<Failure, Unit> {
        return try {
            localDataSource.setCityToPreferences(city, placeOfTheRoute)
            Result.Success(Unit)
        } catch (e: Throwable) {
            Result.Failure(Failure.CustomError(e.stackTraceToString()))
        }
    }


    override suspend fun searchCityByName(name: String): Result<Failure, List<City?>> =
        remoteDataSource.searchCityByName(name)

}