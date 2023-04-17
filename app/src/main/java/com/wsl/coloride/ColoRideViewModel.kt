package com.wsl.coloride

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wsl.coloride.base.BaseEvent

class ColoRideViewModel: ViewModel() {


    protected val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _postBaseEvent = MutableLiveData<BaseEvent>()
    val postBaseEvent: LiveData<BaseEvent>
        get() = _postBaseEvent


    fun postBaseEvent(event: BaseEvent) {
        this._postBaseEvent.postValue(event)
    }
}