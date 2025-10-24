package com.metrolimago

import android.app.Application
import androidx.room.Room
import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.db.MetroDatabase
import com.metrolimago.data.remote.FAKE_API_BASE_URL
import com.metrolimago.data.remote.MetroApiService
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MetroLimaApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database: MetroDatabase by lazy {
        Room.databaseBuilder(
            this,
            MetroDatabase::class.java,
            "metro_database"
        ).build()
    }

    val estacionDao: EstacionDao by lazy { database.estacionDao() }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(FAKE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: MetroApiService by lazy { retrofit.create(MetroApiService::class.java) }

    val repository: MetroRepository by lazy { MetroRepository(estacionDao) }
}
