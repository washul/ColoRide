package com.wsl.data.db.auto

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wsl.domain.model.entities.auto.AutoSelectedEntity

@Dao
interface AutoSelectedDAO {

    @Insert
    suspend fun setAutoSelected(autoSelectedEntity: AutoSelectedEntity)

    @Query("DELETE FROM AutoSelected")
    suspend fun removeAutoSelected()

}