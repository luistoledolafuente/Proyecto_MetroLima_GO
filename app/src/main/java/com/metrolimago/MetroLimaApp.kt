package com.metrolimago

import android.app.Application
import androidx.room.Room
import com.metrolimago.data.db.MetroDatabase
import com.metrolimago.data.repository.MetroRepository

class MetroLimaApp : Application() {

    // Instancia de la base de datos (provisional)
    val database: MetroDatabase by lazy {
        Room.databaseBuilder(
            this,
            MetroDatabase::class.java,
            "metro_lima_db"
        ).build()
    }

    // Repositorio usado por el ViewModel
    val repository: MetroRepository by lazy {
        MetroRepository(database.estacionDao())
    }
}
