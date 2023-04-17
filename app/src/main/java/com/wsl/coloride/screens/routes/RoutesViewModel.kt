package com.wsl.coloride.screens.routes

import android.util.Log
import androidx.lifecycle.*
import com.wsl.coloride.base.BaseEvent
import com.wsl.domain.model.Route
import com.wsl.domain.route.usecase.GetRoutesUseCase
import com.wsl.utils.onFailure
import com.wsl.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class RoutesViewModel(
    private val getRoutesUseCase: GetRoutesUseCase
) : ViewModel() {

    private val _routesList = MutableLiveData<List<Route>>()
    val routesList: LiveData<List<Route>>
        get() = _routesList

    init {
        fetch(Date(), Date())
    }

    fun fetch(start: Date, end: Date) {
        viewModelScope.launch(Dispatchers.IO) {
//            postBaseEvent(BaseEvent.ShowLoading(true))
            getRoutesUseCase(
                GetRoutesUseCase.Params(
                    start,
                    end
                )
            )
                .onFailure { /*postBaseEvent(BaseEvent.HandleFailure(it))*/ }
                .onSuccess {
                    Log.v("FETCH ROUTE LIST", it.toString())
                    _routesList.postValue(it)
//                    postBaseEvent(BaseEvent.ShowLoading(false))
                }
        }
    }

}