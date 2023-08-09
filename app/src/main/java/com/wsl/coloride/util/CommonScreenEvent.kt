package com.wsl.coloride.util

import com.wsl.coloride.ui.main.MainStateViewType

sealed class CommonScreenEvent {

    object OnCloseRequest : CommonScreenEvent()

    object Success : CommonScreenEvent()

    data class ErrorMessage(val message: String) : CommonScreenEvent()

    data class StateViewDialog(val type: MainStateViewType) : CommonScreenEvent()

}