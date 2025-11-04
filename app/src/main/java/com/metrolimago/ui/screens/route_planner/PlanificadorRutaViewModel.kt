package com.metrolimago.ui.screens.route_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.metrolimago.data.model.EstacionEntity
// import com.metrolimago.data.repository.PlanificadorRutaRepository // <-- ELIMINA ESTA LÍNEA
import com.metrolimago.data.repository.MetroRepository // <-- AÑADE ESTA LÍNEA
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow // <-- AÑADIDO
import kotlinx.coroutines.flow.stateIn // <-- AÑADIDO
import com.metrolimago.data.model.RutaPaso // <-- AÑADIDO
import com.metrolimago.data.model.RutaResultado // <-- AÑADIDO
import kotlinx.coroutines.flow.SharingStarted // <-- AÑADIDO
import kotlinx.coroutines.launch

// CORREGIDO: Cambia el tipo de repositorio
class PlanificadorRutaViewModel(private val repository: MetroRepository) : ViewModel() {

    // CORREGIDO: Llama a la función correcta del repositorio
    val todasLasEstaciones: StateFlow<List<EstacionEntity>> = repository.getEstaciones()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _origenSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val origenSeleccionado: StateFlow<EstacionEntity?> = _origenSeleccionado.asStateFlow()

    private val _destinoSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val destinoSeleccionado: StateFlow<EstacionEntity?> = _destinoSeleccionado.asStateFlow()

    private val _rutaCalculada = MutableStateFlow(RutaResultado())
    val rutaCalculada: StateFlow<RutaResultado> = _rutaCalculada.asStateFlow()

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
            // (Aquí sigue tu lógica de cálculo simulada)
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
        // CORREGIDO: El factory ahora pide un MetroRepository
        fun provideFactory(repository: MetroRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlanificadorRutaViewModel(repository) as T
                }
            }
    }
}