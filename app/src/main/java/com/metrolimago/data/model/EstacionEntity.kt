package com.metrolimago.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Esta clase representa la tabla "estaciones" en nuestra base de datos.
 * Cada variable aquí es una columna en la tabla.
 */
@Entity(tableName = "estaciones")
data class EstacionEntity(
    // @PrimaryKey le dice a Room que 'id' es la clave única.
    // autoGenerate = true hace que el número se genere solo (1, 2, 3...).
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Las otras columnas de nuestra tabla.
    val nombre: String,
    val linea: Int,
    val distrito: String
)
