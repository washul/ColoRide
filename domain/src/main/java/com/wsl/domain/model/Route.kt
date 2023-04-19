package com.wsl.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

class Route {

    var uuid: String = UUID.randomUUID().toString()
    lateinit var departureArrival: Pair<City, City>
    lateinit var description: String
    lateinit var auto: Auto
    lateinit var date: LocalDateTime
    lateinit var people: List<User>
    var time: LocalTime? = null
    val owner: User
        get() {return people.find { it.userType == UserType.OWNER }!!}
    val title: String
        get() {
            return "${this.departureArrival.first.name} -> ${this.departureArrival.second.name}"
        }

}