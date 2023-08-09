package com.wsl.domain.auto.usecases

import com.wsl.domain.UseCase
import com.wsl.domain.auto.datasource.AutoDataSource
import com.wsl.domain.auto.mappers.AutoMapper
import com.wsl.domain.model.Auto
import com.wsl.utils.Failure
import com.wsl.utils.Result
import com.wsl.utils.map

class GetAutoSelectedUseCase(private val autoDataSource: AutoDataSource): UseCase<Auto> {

    override suspend fun invoke(): Result<Failure, Auto> {
        val autoMapper = AutoMapper()
        return autoDataSource.getAutoSelected().map {
            autoMapper.mapFromAutoEntity(it)
        }
    }

}