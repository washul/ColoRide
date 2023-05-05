package com.wsl.data.city.mappers

import com.wsl.domain.model.City
import com.wsl.domain.model.CityJsonObject

fun List<CityJsonObject>.mapperCityMapper(): List<City?> =
    this.map { json -> json.let { City(name ="${it.name ?: ""}, ${it.adminName1}, ${it.countryCode}") } }

fun makeCityMapper(): (List<CityJsonObject>) -> List<City?> = { it.mapperCityMapper() }