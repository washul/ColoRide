package com.wsl.domain.city.usecases

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.city.datasource.CityDataSource
import com.wsl.domain.model.City
import com.wsl.utils.Failure
import com.wsl.utils.Result
import com.wsl.utils.map

class SearchCityByNameUseCase(private val dataSource: CityDataSource) :
    UseCaseWithParams<List<City>, SearchCityByNameUseCase.Params> {

    data class Params(val name: String)

    override suspend fun invoke(params: Params): Result<Failure, List<City>> {
        return dataSource.searchCityByName(params.name).map { nulleableCityList ->
            if (!nulleableCityList.isNullOrEmpty()) {

                nulleableCityList.toSet().sortedBy { it!!.name }

                nulleableCityList.requireNoNulls()
            } else {
                emptyList()
            }
        }
    }

}