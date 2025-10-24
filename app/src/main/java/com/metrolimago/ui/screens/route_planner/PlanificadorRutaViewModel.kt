package com.metrolimago.ui.screens.route_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.repository.PlanificadorRutaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanificadorRutaViewModel(private val repository: PlanificadorRutaRepository) : ViewModel() {

    val todasLasEstaciones: StateFlow<List<EstacionEntity>> = repository.getTodasLasEstaciones() as StateFlow<List<EstacionEntity>>

    private val _origenSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val origenSeleccionado: StateFlow<EstacionEntity?> = _origenSeleccionado

    private val _destinoSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val destinoSeleccionado: StateFlow<EstacionEntity?> = _destinoSeleccionado

    private val _rutaCalculada = MutableStateFlow(RutaResultado())
    val rutaCalculada: StateFlow<RutaResultado> = _rutaCalculada

    fun seleccionarOrigen(estacion: EstacionEntity?) {
        _origenSeleccionado.value = estacion
        calcularRuta()
    }

    fun seleccionarDestino(estacion: EstacionEntity?) {
        _destinoSeleccionado.value = estacion
        calcularRuta()
    }

    fun intercambiarOrigenDestino() {
        val temp = _origenSeleccionado.value
        _origenSeleccionado.value = _destinoSeleccionado.value
        _destinoSeleccionado.value = temp
        calcularRuta()
    }

    private fun calcularRuta() {
        val origen = _origenSeleccionado.value
        val destino = _destinoSeleccionado.value
        if (origen != null && destino != null) {
            _rutaCalculada.value = RutaResultado(
                pasos = listOf(
                    RutaPaso(origen.nombre),
                    RutaPaso(destino.nombre)
                ),
                tiempoEstimadoMinutos = 15
            )
        } else {
            _rutaCalculada.value = RutaResultado()
        }
    }

    companion object {
        fun provideFactory(repository: PlanificadorRutaRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlanificadorRutaViewModel(repository) as T
                }
            }
    }
}

// Clases auxiliares
data class RutaResultado(
    val pasos: List<RutaPaso> = emptyList(),
    val tiempoEstimadoMinutos: Int = 0,
    val mensajeError: String? = null
)

data class RutaPaso(val nombreEstacion: String)
