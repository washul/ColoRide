package com.wsl.coloride.screens.create_route

import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsl.domain.auto.usecases.GetAutoSelectedUseCase
import com.wsl.domain.city.usecases.GetCityPreferenceUseCase
import com.wsl.domain.model.Auto
import com.wsl.domain.model.City
import com.wsl.domain.model.PlaceOfTheRoute
import com.wsl.domain.model.User
import com.wsl.domain.model.UserRating
import com.wsl.domain.model.UserType
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class CreateRouteViewModel(
    private val getCityPreferenceUseCase: GetCityPreferenceUseCase,
    private val getAutoSelectedUseCase: GetAutoSelectedUseCase
) : ViewModel() {

    lateinit var user: User
    var arrival: MutableStateFlow<City> = MutableStateFlow(City(""))
        private set
    var departure: MutableStateFlow<City> = MutableStateFlow(City(""))
        private set
    var date: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
        private set
    var time: MutableStateFlow<LocalTime> = MutableStateFlow(LocalTime.now())
        private set
    var description: MutableStateFlow<String> = MutableStateFlow("")
        private set
    var doesOwnerNeedsApprove: MutableStateFlow<Boolean> = MutableStateFlow(true)
        private set
    var auto: MutableStateFlow<Auto> = MutableStateFlow(Auto())
        private set
    var isSaveButtonAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set


    private val _onEvent = MutableStateFlow<CreateRouteEvent>(CreateRouteEvent.Success)
    val onEvent: StateFlow<CreateRouteEvent> get() = _onEvent

    init {
        this.user = User(
            "123",
            "WSL",
            "wsl@wsl.com",
            "1234567890",
            Uri.EMPTY,
            UserRating(3f, 3f, 3f),
            UserType.OWNER
        )
        if (!::user.isInitialized) {
            postEvent(CreateRouteEvent.NoUserFind)
        }

        getAutoSelected()
    }

    fun createRoute() {

    }

    //View model Logic
    private fun getAutoSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            getAutoSelectedUseCase().onSuccess {
                auto.value = it
            }
        }
    }

    fun setDate(date: LocalDate) {
        this.date.value = date
    }

    fun setTime(time: LocalTime) {
        this.time.value = time
    }

    fun setDescription(text: String) {
        this.description.value = text
    }

    fun doesOwnerNeedsApprove(it: Boolean) {
        this.doesOwnerNeedsApprove.value = it
    }

    fun setCreateButtonEnable(isEnable: Boolean) {
        isSaveButtonAvailable.value = isEnable
    }

    fun postEvent(event: CreateRouteEvent) = viewModelScope.launch {
        _onEvent.emit(event)
    }

    //DataSources
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

}