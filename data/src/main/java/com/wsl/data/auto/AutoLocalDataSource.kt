package com.wsl.data.auto

import com.wsl.data.db.auto.AutoDAO
import com.wsl.data.db.auto.AutoSelectedDAO
import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.domain.model.entities.auto.AutoSelectedEntity
import com.wsl.utils.Failure
import com.wsl.utils.Result

class AutoLocalDataSource(
    private val autoDao: AutoDAO,
    private val autoSelectedDAO: AutoSelectedDAO
) {

    suspend fun insertAutoValue(autoEntity: AutoEntity) {
        autoDao.insertValue(autoEntity)
    }

    suspend fun insertAutoSelected(autoEntityID: String): Result<Failure, Unit> {
        return try {
            autoSelectedDAO.removeAutoSelected()
            autoSelectedDAO.setAutoSelected(AutoSelectedEntity(autoID = autoEntityID))
            Result.Success(Unit)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Failure(Failure.CustomError("Something went wrong"))
        }
    }

    suspend fun getAutoValue(): List<AutoEntity> = autoDao.getValue()

    suspend fun getAutoSelected(): Result<Failure, AutoEntity> =
        try {
            val auto = autoDao.getAutoSelected()
            if(auto == null){
                Result.Failure(Failure.CustomError("No auto founded"))
            } else {
                Result.Success(auto)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Failure(Failure.CustomError("Something went wrong"))
        }

}