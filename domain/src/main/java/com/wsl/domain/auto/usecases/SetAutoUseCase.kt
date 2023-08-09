package com.wsl.domain.auto.usecases

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.auto.datasource.AutoDataSource
import com.wsl.domain.auto.mappers.AutoMapper
import com.wsl.domain.model.Auto
import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.utils.Failure
import com.wsl.utils.Result
import java.util.UUID

class SetAutoUseCase(
    private val autoDataSource: AutoDataSource,
    private val setAutoAsSelectedUseCase: SetAutoAsSelectedUseCase
) : UseCaseWithParams<Boolean, SetAutoUseCase.Params> {

    data class Params(
        val auto: Auto
    )

    private val autoMapper = AutoMapper()

    override suspend fun invoke(params: Params): Result<Failure, Boolean> {

        val uuid = UUID.randomUUID().toString()
        val autoEntity = AutoEntity(
            uuid = uuid,
            name = params.auto.name,
            image = params.auto.image.toString(),
            seatNumber = params.auto.seatNumber
        )

        setAutoAsSelectedUseCase(
            SetAutoAsSelectedUseCase.Params(
                autoMapper.mapFromAutoEntity(autoEntity)
            )
        )

        return autoDataSource.insertAutoValueToLocalSource(
            autoEntity
        )

    }


}