package com.metrolimago.ui.screens.route_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.metrolimago.data.dao.EstacionDao
import com.metrolimago.data.repository.MetroRepository
import com.metrolimago.data.model.EstacionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Modelo de cada paso de ruta
data class RutaPaso(val nombreEstacion: String)
data class RutaResultado(
    val pasos: List<RutaPaso> = emptyList(),
    val tiempoEstimadoMinutos: Int = 0,
    val mensajeError: String? = null
)

class PlanificadorRutaViewModel(
    private val estacionDao: EstacionDao,
    private val repository: MetroRepository
) : ViewModel() {

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
            estacionDao.getEstaciones().collect { estaciones ->
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

    fun intercambiarOrigenDestino() {
        val temp = _origenSeleccionado.value
        _origenSeleccionado.value = _destinoSeleccionado.value
        _destinoSeleccionado.value = temp
        calcularRuta()
    }

    private fun calcularRuta() {
        val origen = _origenSeleccionado.value
        val destino = _destinoSeleccionado.value

        if (origen == null || destino == null) {
            _rutaCalculada.value = RutaResultado()
            return
        }

        // Aquí puedes usar repository o algún algoritmo real de rutas
        val pasos = listOf(
            RutaPaso(origen.nombre),
            RutaPaso("Intermedia 1"),
            RutaPaso("Intermedia 2"),
            RutaPaso(destino.nombre)
        )
        val tiempo = pasos.size * 3 // minutos aproximados
        _rutaCalculada.value = RutaResultado(pasos = pasos, tiempoEstimadoMinutos = tiempo)
    }

    companion object {
        fun provideFactory(
            estacionDao: EstacionDao,
            repository: MetroRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PlanificadorRutaViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return PlanificadorRutaViewModel(estacionDao, repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
