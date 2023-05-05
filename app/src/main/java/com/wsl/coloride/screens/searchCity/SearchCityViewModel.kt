package com.wsl.coloride.screens.searchCity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsl.domain.city.usecases.SearchCityByNameUseCase
import com.wsl.domain.city.usecases.SetCityPreferenceUseCase
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SearchCityViewModel(
    private val searchCityByNameUseCase: SearchCityByNameUseCase,
    private val setCityPreferenceUseCase: SetCityPreferenceUseCase,
) : ViewModel() {

    private var cities = MutableStateFlow<List<City>>(emptyList())
    private var searchText = MutableStateFlow("")
    private lateinit var placeOfTheRoute: PlaceOfTheRoute

    val citySearchModelState = combine(searchText, cities) { text, matchedCity ->
        CitySearchModelState(
            searchText = text,
            cities = matchedCity
        )
    }

    fun setPlaceOfTheRoute(placeOfTheRoute: PlaceOfTheRoute) {
        this.placeOfTheRoute = placeOfTheRoute
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            setCityPreferenceUseCase(
                SetCityPreferenceUseCase.Params(
                    city = city,
                    placeOfTheRoute
                )
            ).onFailure {
                Log.e("SetCityToPReference failure: ", "${it.getError()}")
            }
        }

    }

    private fun searchCityByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchCityByNameUseCase(
                SearchCityByNameUseCase.Params(
                    name = name
                )
            )
                .onFailure { /**Handle failure*/ }
                .onSuccess {
                    cities.emit(it)
                }
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if (changedSearchText.isEmpty() || changedSearchText.length < 3) {
            cities.value = arrayListOf()
            return
        }
        searchCityByName(changedSearchText)
    }

    fun onClearClick() {
        searchText.value = ""
        cities.value = arrayListOf()
    }

}

data class CitySearchModelState(
    val searchText: String = "",
    val cities: List<City> = arrayListOf()
) {
    companion object {
        val Empty = CitySearchModelState()
    }
}