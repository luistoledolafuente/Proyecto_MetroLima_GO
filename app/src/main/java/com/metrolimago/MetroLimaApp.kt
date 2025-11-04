package com.metrolimago

import android.app.Application
import androidx.room.Room // <-- IMPORTANTE
import com.metrolimago.data.db.MetroDatabase
import com.metrolimago.data.remote.FAKE_API_BASE_URL
import com.metrolimago.data.remote.MetroApiService
import com.metrolimago.data.repository.MetroRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class MetroLimaApp : Application() {

    // ESTA ES LA LÍNEA CORREGIDA
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MetroDatabase::class.java,
            "metro_database" // Nombre del archivo de la base de datos
        ).build()
    }

    private val apiService: MetroApiService by lazy {
        Retrofit.Builder()
            .baseUrl(FAKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MetroApiService::class.java)
    }

    lateinit var repository: MetroRepository

    override fun onCreate() {
        super.onCreate()
        repository = MetroRepository(database.estacionDao(), apiService)
    }
}