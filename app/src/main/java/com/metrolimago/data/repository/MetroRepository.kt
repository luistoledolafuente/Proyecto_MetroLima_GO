package com.metrolimago.data.repository

import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.model.Alerta
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.remote.MetroApiService // <-- AÑADIDO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response // <-- AÑADIDO

/**
 * Repositorio unificado que maneja la API (ApiService) y la Base de Datos (Dao)
 */
class MetroRepository(
    private val estacionDao: EstacionDao,
    private val apiService: MetroApiService // <-- AÑADIDO
) {

    // --- Funciones de Base de Datos (DAO) ---

    /**
     * Obtiene todas las estaciones desde la base de datos local.
     */
    fun getEstaciones(): Flow<List<EstacionEntity>> = estacionDao.getEstaciones()

    /**
     * Busca estaciones en la base de datos local.
     */
    fun searchEstaciones(query: String): Flow<List<EstacionEntity>> = estacionDao.searchEstaciones(query)

    /**
     * Inserta una lista de estaciones en la base de datos local.
     */
    suspend fun insertAllEstaciones(estaciones: List<EstacionEntity>) = estacionDao.insertAll(estaciones)


    // --- Funciones de Red (API) ---

    /**
     * Obtiene las alertas de servicio desde la API remota.
     * (Esta era la función que faltaba)
     */
    suspend fun syncAlertas(): Response<List<Alerta>> {
        return apiService.obtenerAlertas()
    }
}