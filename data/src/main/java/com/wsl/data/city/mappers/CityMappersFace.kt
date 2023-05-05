package com.wsl.data.city.mappers

import com.wsl.domain.model.City
import com.wsl.domain.model.CityJsonObject

class CityMappersFace(
    val makeCityListMapper: (List<CityJsonObject>) -> List<City>
)