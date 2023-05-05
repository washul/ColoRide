package com.wsl.data.city

import com.wsl.data.preferences.MainUserPreferences
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute

class CityLocalDataSource(private val preferences: MainUserPreferences) {

    suspend fun getCityFromPreferences(placeOfTheRoute: PlaceOfTheRoute): City? {
        return when(placeOfTheRoute){
            PlaceOfTheRoute.Arrival -> preferences.cityArrivalPreference
            PlaceOfTheRoute.Departure -> preferences.cityDeparturePreference
            else -> {preferences.cityDeparturePreference}
        }
    }

    suspend fun setCityToPreferences(city: City, placeOfTheRoute: PlaceOfTheRoute) {
        when(placeOfTheRoute){
            PlaceOfTheRoute.Arrival -> preferences.cityArrivalPreference = city
            PlaceOfTheRoute.Departure -> preferences.cityDeparturePreference = city
            else -> { preferences.cityDeparturePreference }
        }

    }

}