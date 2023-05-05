package com.wsl.coloride.screens.create_route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsl.domain.city.usecases.GetCityPreferenceUseCase
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateRouteViewModel(
    private val getCityPreferenceUseCase: GetCityPreferenceUseCase
) : ViewModel() {

    var arrival: MutableStateFlow<City> = MutableStateFlow(City(""))
        private set
    var departure: MutableStateFlow<City> = MutableStateFlow(City(""))
        private set

    private val _onEvent = MutableStateFlow<CreateRouteEvent>(CreateRouteEvent.Success)
    val onEvent: StateFlow<CreateRouteEvent> get() = _onEvent


    fun getDepartureFromPreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            getCityPreferenceUseCase(
                GetCityPreferenceUseCase.Params(
                    PlaceOfTheRoute.Departure
                )
            ).onFailure {
                //Handle event to set departure city
                postEvent(CreateRouteEvent.LookingForDepartureCity)
            }.onSuccess {
                departure.emit(it)
            }
        }
    }

    fun getArrivalFromPreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            getCityPreferenceUseCase(
                GetCityPreferenceUseCase.Params(
                    PlaceOfTheRoute.Arrival
                )
            ).onFailure {
                //Handle event to set departure city
                postEvent(CreateRouteEvent.LookingForArrivalCity)
            }.onSuccess {
                arrival.emit(it)
            }
        }
    }


    fun postEvent(event: CreateRouteEvent) = viewModelScope.launch {
        _onEvent.emit(event)
    }


}