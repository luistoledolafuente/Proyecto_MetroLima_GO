package com.metrolimago.data.remote

import com.metrolimago.data.model.Alerta
import retrofit2.Response
import retrofit2.http.GET

// ASEGÚRATE DE QUE ESTA LÍNEA SEA EXACTAMENTE ASÍ:
const val FAKE_API_BASE_URL = "https://my-json-server.typicode.com/luistoledolafuente/fake-metro-api/"

interface MetroApiService {
    // ASEGÚRATE DE QUE ESTA LÍNEA SEA EXACTAMENTE ASÍ:
    @GET("alertas") // Sin barra al principio
    suspend fun obtenerAlertas(): Response<List<Alerta>>
}