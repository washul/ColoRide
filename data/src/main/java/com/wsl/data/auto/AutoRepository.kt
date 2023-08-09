package com.wsl.data.auto

import com.wsl.domain.auto.datasource.AutoDataSource
import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.utils.Failure
import com.wsl.utils.Result

class AutoRepository(
    private val remoteDataSource: AutoRemoteDataSource,
    private val localDataSource: AutoLocalDataSource
) : AutoDataSource {

    override suspend fun getAutoValueFromLocalSource(): List<AutoEntity> =
        localDataSource.getAutoValue()
    override suspend fun insertAutoValueToLocalSource(autoEntity: AutoEntity): Result<Failure, Boolean> {
        return try {
            localDataSource.insertAutoValue(autoEntity)
            Result.Success(true)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Failure(Failure.CustomError("Something went wrong during saving the auto"))
        }
    }

    override suspend fun setAutoAsSelected(autoEntity: AutoEntity): Result<Failure, Unit> =
        localDataSource.insertAutoSelected(autoEntity.uuid)

    override suspend fun getAutoSelected(): Result<Failure, AutoEntity> =
        localDataSource.getAutoSelected()

}