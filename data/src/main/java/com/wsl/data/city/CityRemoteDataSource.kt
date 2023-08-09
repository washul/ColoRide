package com.wsl.data.city

import com.wsl.data.city.apiservice.CityApiService
import com.wsl.data.city.mappers.CityMappersFace
import com.wsl.data.city.mappers.mapperCityMapper
import com.wsl.domain.model.City
import com.wsl.domain.model.CityJsonObject
import com.wsl.domain.model.MainCityApiResponseObject
import com.wsl.utils.Failure
import com.wsl.utils.Result
import com.wsl.utils.map
import com.wsl.utils.request

class CityRemoteDataSource(
    private val service: CityApiService,
    private val mapperFace: CityMappersFace
) {

    suspend fun searchCityByName(name: String): Result<Failure, List<City?>> {
        val response = service.searchCity(name)
        return request(
            { response },
            MainCityApiResponseObject()
        ).map {
            it.geonames.mapperCityMapper()
        }
    }



}