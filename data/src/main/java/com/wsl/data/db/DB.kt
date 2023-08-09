package com.wsl.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wsl.data.db.auto.AutoDAO
import com.wsl.data.db.auto.AutoSelectedDAO
import com.wsl.domain.model.entities.auto.AutoEntity
import com.wsl.domain.model.entities.auto.AutoSelectedEntity

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [AutoEntity::class, AutoSelectedEntity::class], version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {

    abstract fun autoDao(): AutoDAO
    abstract fun autoSelectedDao(): AutoSelectedDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DB? = null

        fun getDatabase(context: Context): DB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}