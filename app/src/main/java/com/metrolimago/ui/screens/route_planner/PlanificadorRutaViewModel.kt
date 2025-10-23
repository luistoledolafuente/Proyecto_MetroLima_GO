package com.metrolimago.ui.screens.route_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Estructura para representar un paso en la ruta calculada
data class RutaPaso(val nombreEstacion: String)

// Estructura para el resultado de la ruta calculada
data class RutaResultado(
    val pasos: List<RutaPaso> = emptyList(),
    val tiempoEstimadoMinutos: Int = 0,
    val mensajeError: String? = null
)

class PlanificadorRutaViewModel(private val repository: MetroRepository) : ViewModel() {

    private val _todasLasEstaciones = MutableStateFlow<List<EstacionEntity>>(emptyList())
    val todasLasEstaciones: StateFlow<List<EstacionEntity>> = _todasLasEstaciones.asStateFlow()

    private val _origenSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val origenSeleccionado: StateFlow<EstacionEntity?> = _origenSeleccionado.asStateFlow()

    private val _destinoSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val destinoSeleccionado: StateFlow<EstacionEntity?> = _destinoSeleccionado.asStateFlow()

    private val _rutaCalculada = MutableStateFlow(RutaResultado())
    val rutaCalculada: StateFlow<RutaResultado> = _rutaCalculada.asStateFlow()

    init {
        viewModelScope.launch {
            repository.todasLasEstaciones.collect { estaciones ->
                _todasLasEstaciones.value = estaciones
            }
        }
    }

    fun seleccionarOrigen(estacion: EstacionEntity?) {
        _origenSeleccionado.value = estacion
        calcularRuta()
    }

    fun seleccionarDestino(estacion: EstacionEntity?) {
        _destinoSeleccionado.value = estacion
        calcularRuta()
    }

    private fun calcularRuta() {
        val origen = _origenSeleccionado.value
        val destino = _destinoSeleccionado.value
        val estaciones = _todasLasEstaciones.value

        if (origen == null || destino == null) {
            _rutaCalculada.value = RutaResultado()
            return
        }
        if (origen == destino) {
            _rutaCalculada.value = RutaResultado(mensajeError = "El origen y destino no pueden ser iguales.")
            return
        }
        if (estaciones.isEmpty()) {
            _rutaCalculada.value = RutaResultado(mensajeError = "Cargando estaciones...")
            return
        }

        val indexOrigen = estaciones.indexOf(origen)
        val indexDestino = estaciones.indexOf(destino)

        if (indexOrigen == -1 || indexDestino == -1) {
            _rutaCalculada.value = RutaResultado(mensajeError = "Estaciones no encontradas en la lista.")
            return
        }

        val pasosEstaciones = if (indexOrigen < indexDestino) {
            estaciones.subList(indexOrigen, indexDestino + 1)
        } else {
            estaciones.subList(indexDestino, indexOrigen + 1).reversed()
        }

        val pasosRuta = pasosEstaciones.map { RutaPaso(it.nombre) }
        val tiempoEstimado = (pasosRuta.size - 1) * 3

        _rutaCalculada.value = RutaResultado(pasos = pasosRuta, tiempoEstimadoMinutos = tiempoEstimado)
    }
}
