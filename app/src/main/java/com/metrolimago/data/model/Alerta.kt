package com.metrolimago.data.model

data class Alerta(
    val id: Int,
    val titulo: String,
    val mensaje: String,
    val tipo: String // "INFO", "ADVERTENCIA", "PELIGRO"
)
