package com.wsl.coloride.screens.create_route

sealed class CreateRouteEvent {

    object LookingForDepartureCity: CreateRouteEvent()

    object LookingForArrivalCity: CreateRouteEvent()

    object LoadingShouldShow: CreateRouteEvent()

    object Success: CreateRouteEvent()

}