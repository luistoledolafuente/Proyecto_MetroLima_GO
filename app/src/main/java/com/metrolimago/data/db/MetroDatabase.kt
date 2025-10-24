package com.metrolimago.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.model.EstacionEntity

@Database(entities = [EstacionEntity::class], version = 1, exportSchema = false)
abstract class MetroDatabase : RoomDatabase() {
    abstract fun estacionDao(): EstacionDao
}
