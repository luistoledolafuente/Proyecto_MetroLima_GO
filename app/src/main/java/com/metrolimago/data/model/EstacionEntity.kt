package com.metrolimago.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estaciones")
data class EstacionEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val linea: String,
    val distrito: String,
    val latitud: Double,
    val longitud: Double,
    val horario: String
)
