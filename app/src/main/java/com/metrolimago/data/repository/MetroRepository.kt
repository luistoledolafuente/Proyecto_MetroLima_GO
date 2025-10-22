package com.metrolimago.data.repository

import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.remote.MetroApiService

class MetroRepository(
    private val estacionDao: EstacionDao,
    private val apiService: MetroApiService // Añadir el servicio API
) {
    val todasLasEstaciones = estacionDao.obtenerTodas()

    // Nueva función para obtener alertas desde la red
    suspend fun obtenerAlertasDesdeApi() = apiService.obtenerAlertas()
}
