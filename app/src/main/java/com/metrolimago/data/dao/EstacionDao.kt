package com.metrolimago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metrolimago.data.model.EstacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EstacionDao {
    @Query("SELECT * FROM estaciones ORDER BY nombre ASC")
    fun getEstaciones(): Flow<List<EstacionEntity>>

    @Query("SELECT * FROM estaciones WHERE nombre LIKE '%' || :query || '%' OR linea LIKE '%' || :query || '%'")
    fun searchEstaciones(query: String): Flow<List<EstacionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(estaciones: List<EstacionEntity>)
}
