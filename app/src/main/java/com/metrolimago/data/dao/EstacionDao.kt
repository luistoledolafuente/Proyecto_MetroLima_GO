package com.metrolimago.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metrolimago.data.model.EstacionEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) para las estaciones.
 * Aquí definimos todas las operaciones que podemos hacer en la tabla "estaciones".
 */
@Dao
interface EstacionDao {

    /**
     * Inserta una lista de estaciones en la base de datos.
     * onConflict = OnConflictStrategy.REPLACE: Si intentamos insertar una estación
     * que ya existe (mismo id), la reemplazará con la nueva.
     * 'suspend' significa que esta operación debe llamarse desde una corrutina (es asíncrona).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(estaciones: List<EstacionEntity>)

    /**
     * Obtiene todas las estaciones de la tabla, ordenadas por nombre.
     * Devuelve un 'Flow', lo que es genial porque si los datos cambian
     * (insertamos, borramos, etc.), el 'Flow' nos avisará automáticamente con la nueva lista.
     * La UI se actualizará sola.
     */
    @Query("SELECT * FROM estaciones ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<EstacionEntity>>
}
