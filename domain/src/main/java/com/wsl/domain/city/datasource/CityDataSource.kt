package com.wsl.domain.city.datasource

import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.Failure
import com.wsl.utils.Result

interface CityDataSource {

    suspend fun getCityFromPreferences(placeOfTheRoute: PlaceOfTheRoute): City?

    suspend fun setCityDepartureToPreferences(city: City, placeOfTheRoute: PlaceOfTheRoute): Result<Failure, Unit>

    suspend fun searchCityByName(name: String): Result<Failure, List<City?>>

}