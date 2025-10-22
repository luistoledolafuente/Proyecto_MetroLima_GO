package com.metrolimago.data.remote

import com.metrolimago.data.model.Alerta
import retrofit2.Response
import retrofit2.http.GET

// URL base de la API falsa (para pruebas)
const val FAKE_API_BASE_URL = "https://my-json-server.typicode.com/luistoledolafuente/fake-metro-api/"

interface MetroApiService {
    @GET("alertas")
    suspend fun obtenerAlertas(): Response<List<Alerta>>
}
