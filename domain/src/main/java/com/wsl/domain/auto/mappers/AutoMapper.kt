package com.wsl.domain.auto.mappers

import android.net.Uri
import com.wsl.domain.model.Auto
import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.domain.utils.EntityMapper

class AutoMapper : EntityMapper<AutoEntity, Auto> {
    override fun mapFromAutoEntity(autoEntity: AutoEntity): Auto {
        return Auto(
            uuid = autoEntity.uuid,
            name = autoEntity.name,
            image = Uri.parse(autoEntity.image),
            seatNumber = autoEntity.seatNumber,
            isSelected = false
        )
    }

    override fun mapToAutoEntity(auto: Auto): AutoEntity {
        return AutoEntity(
            uuid = auto.uuid,
            name = auto.name,
            image = auto.image.toString(),
            seatNumber = auto.seatNumber
        )
    }

    fun mapToAutoEntityList(list: List<Auto>): List<AutoEntity> = list.map { mapToAutoEntity(it) }

    fun mapFromAutoEntity(list: List<AutoEntity>): List<Auto> = list.map { mapFromAutoEntity(it) }
}