package com.metrolimago.data.repository

import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.remote.MetroApiService
import kotlinx.coroutines.flow.Flow

class PlanificadorRutaRepository(
    private val estacionDao: EstacionDao,
    private val apiService: MetroApiService
) {

    // Trae todas las estaciones de la base de datos
    fun getTodasLasEstaciones(): Flow<List<EstacionEntity>> = estacionDao.getEstaciones()

    // Función de ejemplo para obtener alertas desde la API
    suspend fun obtenerAlertas(): List<String> {
        val response = apiService.obtenerAlertas()
        if (response.isSuccessful) {
            return response.body()?.map { it.mensaje } ?: emptyList()
        } else {
            throw Exception("Error al obtener alertas: ${response.code()}")
        }
    }
}
