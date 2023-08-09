package com.wsl.domain.utils


interface EntityMapper <Entity, DomainModel> {

    fun mapFromAutoEntity(autoEntity: Entity): DomainModel

    fun mapToAutoEntity(auto: DomainModel): Entity

}