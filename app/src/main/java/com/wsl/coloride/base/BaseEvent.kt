package com.wsl.coloride.base

import com.wsl.utils.Failure

sealed class BaseEvent {
    data class ShowSnackBar(val message: String): BaseEvent()
    data class HandleFailure(val failure: Failure): BaseEvent()
    data class ShowLoading(val doShow: Boolean = false): BaseEvent()
}