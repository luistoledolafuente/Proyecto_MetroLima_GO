package com.metrolimago

import android.app.Application
import com.metrolimago.data.db.MetroDatabase
import com.metrolimago.data.remote.FAKE_API_BASE_URL
import com.metrolimago.data.remote.MetroApiService
import com.metrolimago.data.repository.MetroRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class MetroLimaApp : Application() {

    // Instancia de la Base de Datos
    private val database by lazy { MetroDatabase.getDatabase(this) }

    // 1. Crear la instancia de ApiService (Retrofit)
    private val apiService: MetroApiService by lazy {
        Retrofit.Builder()
            .baseUrl(FAKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetroApiService::class.java)
    }

    // 2. Definir el repositorio (se inicializará en onCreate)
    lateinit var repository: MetroRepository

    override fun onCreate() {
        super.onCreate()

        // 3. Inicializar el repositorio UNIFICADO con AMBOS argumentos
        repository = MetroRepository(database.estacionDao(), apiService)
    }
}