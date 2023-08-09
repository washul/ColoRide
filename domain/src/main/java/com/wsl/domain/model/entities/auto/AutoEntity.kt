package com.wsl.domain.model.entities.auto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity("AutoEntity")
data class AutoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String,
    var name: String = "",
    val image: String? = null,
    var seatNumber: Int = 5
)


