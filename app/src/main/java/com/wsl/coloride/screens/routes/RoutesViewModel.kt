package com.wsl.coloride.screens.routes

import android.util.Log
import androidx.lifecycle.*
import com.wsl.coloride.base.BaseEvent
import com.wsl.data.route.COLOTLAN_CITY
import com.wsl.data.route.GDL_CITY
import com.wsl.domain.model.Route
import com.wsl.domain.route.usecase.GetRoutesUseCase
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date

class RoutesViewModel(
    private val getRoutesUseCase: GetRoutesUseCase
) : ViewModel() {

    private val _routesList = MutableLiveData<Map<Int, List<Route>>>()
    val routesList: LiveData<Map<Int, List<Route>>>
        get() = _routesList

    init {
        fetch()
    }

    fun fetch(
        startDate: LocalDateTime = LocalDateTime.now(),
        endDate: LocalDateTime = startDate.plusDays(7),
        cityDeparture: String = COLOTLAN_CITY.name,
        cityArrival: String = GDL_CITY.name

    ) {
        viewModelScope.launch(Dispatchers.IO) {
//            postBaseEvent(BaseEvent.ShowLoading(true))
            getRoutesUseCase(
                GetRoutesUseCase.Params(
                    startDate,
                    endDate,
                    cityDeparture,
                    cityArrival
                )
            )
                .onFailure { /*postBaseEvent(BaseEvent.HandleFailure(it))*/ }
                .onSuccess {
//                    Log.v("FETCH ROUTE LIST", it.toString())
                    _routesList.postValue(it)
//                    postBaseEvent(BaseEvent.ShowLoading(false))
                }
        }
    }

}