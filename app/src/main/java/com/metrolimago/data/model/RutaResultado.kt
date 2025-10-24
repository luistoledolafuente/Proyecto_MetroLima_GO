package com.metrolimago.data.model

data class RutaResultado(
    val pasos: List<RutaPaso> = emptyList(),
    val tiempoEstimadoMinutos: Int = 0,
    val mensajeError: String? = null
)
