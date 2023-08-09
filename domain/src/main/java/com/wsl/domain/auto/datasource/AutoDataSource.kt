package com.wsl.domain.auto.datasource

import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.utils.Failure
import com.wsl.utils.Result

interface AutoDataSource {

    suspend fun insertAutoValueToLocalSource(autoEntity: AutoEntity): Result<Failure, Boolean>

    suspend fun getAutoValueFromLocalSource(): List<AutoEntity>

    suspend fun getAutoSelected(): Result<Failure, AutoEntity>

    suspend fun setAutoAsSelected(autoEntity: AutoEntity): Result<Failure, Unit>


}