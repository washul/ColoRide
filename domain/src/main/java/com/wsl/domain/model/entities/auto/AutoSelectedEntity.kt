package com.wsl.domain.model.entities.auto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AutoSelected")
data class AutoSelectedEntity(
    @PrimaryKey
    val pk: Int = 0,
    val autoID: String
)