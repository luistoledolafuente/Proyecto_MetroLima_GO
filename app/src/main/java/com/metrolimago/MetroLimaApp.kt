package com.metrolimago

import android.app.Application
import com.metrolimago.data.db.MetroDatabase
import com.metrolimago.data.remote.FAKE_API_BASE_URL
import com.metrolimago.data.remote.MetroApiService
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MetroLimaApp : Application() {

    val applicationScope by lazy { CoroutineScope(SupervisorJob()) }

    // Base de datos (usa el método recomendado)
    val database by lazy { MetroDatabase.getDatabase(this, applicationScope) }

    // --- NUEVO: configuración de Retrofit ---
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(FAKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia del servicio API
    private val apiService by lazy {
        retrofit.create(MetroApiService::class.java)
    }

    // Repositorio con ambas dependencias
    val repository by lazy {
        MetroRepository(database.estacionDao(), apiService)
    }
}
