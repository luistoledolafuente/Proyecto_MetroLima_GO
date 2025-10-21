package com.metrolimago.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.model.EstacionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Esta es la clase principal de nuestra base de datos Room.
 * Une todas las piezas: las entidades (tablas) y los DAOs (comandos).
 */
@Database(
    entities = [EstacionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MetroDatabase : RoomDatabase() {

    abstract fun estacionDao(): EstacionDao

    // --- ¡AQUÍ ESTÁ LA MAGIA! ---
    // Un 'Companion Object' es como una sección de variables y funciones estáticas.
    // Esto nos permite acceder a ellas desde cualquier parte de la app sin crear una instancia de MetroDatabase.
    companion object {

        // '@Volatile' asegura que la variable INSTANCE sea siempre la más actualizada
        // para todos los hilos de ejecución. Es una buena práctica para la base de datos.
        @Volatile
        private var INSTANCE: MetroDatabase? = null

        // Esta es la función principal que usaremos para obtener nuestra base de datos.
        fun getDatabase(context: Context, scope: CoroutineScope): MetroDatabase {
            // Si la instancia ya existe, la devolvemos.
            // Si no, la creamos en un bloque 'synchronized' para evitar que dos hilos la creen a la vez.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroDatabase::class.java,
                    "metro_database" // Nombre del archivo de la base de datos en el dispositivo
                )
                    .addCallback(MetroDatabaseCallback(scope)) // ¡Añadimos nuestro callback aquí!
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Este es el Callback que se ejecutará UNA SOLA VEZ cuando la base de datos se cree.
         */
        private class MetroDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Cuando la base de datos se crea, llenamos los datos iniciales.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.estacionDao())
                    }
                }
            }

            /**
             * Esta función contiene la lista completa de estaciones de la Línea 1
             * y las inserta en la base de datos.
             */
            suspend fun populateDatabase(estacionDao: EstacionDao) {
                val estacionesLinea1 = listOf(
                    EstacionEntity(nombre = "Bayóvar", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "Santa Rosa", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "San Martín", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "San Carlos", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "Los Postes", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "Los Jardines", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "Pirámide del Sol", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "Caja de Agua", linea = 1, distrito = "San Juan de Lurigancho"),
                    EstacionEntity(nombre = "Presbítero Maestro", linea = 1, distrito = "Lima"),
                    EstacionEntity(nombre = "El Ángel", linea = 1, distrito = "El Agustino"),
                    EstacionEntity(nombre = "Miguel Grau", linea = 1, distrito = "La Victoria"),
                    EstacionEntity(nombre = "Gamarra", linea = 1, distrito = "La Victoria"),
                    EstacionEntity(nombre = "Arriola", linea = 1, distrito = "La Victoria"),
                    EstacionEntity(nombre = "La Cultura", linea = 1, distrito = "San Borja"),
                    EstacionEntity(nombre = "San Borja Sur", linea = 1, distrito = "San Borja"),
                    EstacionEntity(nombre = "Angamos", linea = 1, distrito = "San Borja"),
                    EstacionEntity(nombre = "Cabitos", linea = 1, distrito = "Santiago de Surco"),
                    EstacionEntity(nombre = "Ayacucho", linea = 1, distrito = "Santiago de Surco"),
                    EstacionEntity(nombre = "Jorge Chávez", linea = 1, distrito = "Santiago de Surco"),
                    EstacionEntity(nombre = "Atocongo", linea = 1, distrito = "San Juan de Miraflores"),
                    EstacionEntity(nombre = "San Juan", linea = 1, distrito = "San Juan de Miraflores"),
                    EstacionEntity(nombre = "María Auxiliadora", linea = 1, distrito = "Villa María del Triunfo"),
                    EstacionEntity(nombre = "Villa María", linea = 1, distrito = "Villa María del Triunfo"),
                    EstacionEntity(nombre = "Pumacahua", linea = 1, distrito = "Villa María del Triunfo"),
                    EstacionEntity(nombre = "Parque Industrial", linea = 1, distrito = "Villa El Salvador"),
                    EstacionEntity(nombre = "Villa El Salvador", linea = 1, distrito = "Villa El Salvador")
                )
                estacionDao.insertarTodas(estacionesLinea1)
            }
        }
    }
}

