package com.wsl.domain.auto.usecases

import com.wsl.domain.UseCaseWithParams
import com.wsl.domain.auto.datasource.AutoDataSource
import com.wsl.domain.auto.mappers.AutoMapper
import com.wsl.domain.model.Auto
import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.utils.Failure
import com.wsl.utils.Result

class SetAutoAsSelectedUseCase(private val autoDataSource: AutoDataSource) :
    UseCaseWithParams<Unit, SetAutoAsSelectedUseCase.Params> {

    data class Params(val auto: Auto)

    private val autoMapper = AutoMapper()

    override suspend fun invoke(params: Params): Result<Failure, Unit> {
        return autoDataSource.setAutoAsSelected(autoEntity = autoMapper.mapToAutoEntity(params.auto))
    }

}