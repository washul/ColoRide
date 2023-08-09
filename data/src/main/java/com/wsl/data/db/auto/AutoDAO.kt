package com.wsl.data.db.auto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wsl.domain.model.entities.auto.AutoEntity

@Dao
interface AutoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValue(autoEntity: AutoEntity)

    @Query("SELECT * FROM AutoEntity")
    suspend fun getValue(): List<AutoEntity>

    @Query("SELECT a.* FROM AutoEntity a, AutoSelected aSelected WHERE a.uuid = aSelected.autoID")
    suspend fun getAutoSelected(): AutoEntity?

}