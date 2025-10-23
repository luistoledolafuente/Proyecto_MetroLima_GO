package com.metrolimago.ui.screens.route_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.metrolimago.MetroLimaApp // Asegúrate que este archivo exista según el plan
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// --- Data Classes para la UI ---

/** Representa un paso individual en la ruta calculada. */
data class RutaPaso(val nombreEstacion: String)

/** Contiene el resultado completo del cálculo de la ruta. */
data class RutaResultado(
    val pasos: List<RutaPaso> = emptyList(),
    val tiempoEstimadoMinutos: Int = 0,
    val mensajeError: String? = null // Para mostrar errores al usuario
)

// --- ViewModel ---

class PlanificadorRutaViewModel(private val repository: MetroRepository) : ViewModel() {

    // --- StateFlows para la UI ---

    // Guarda la lista completa de estaciones obtenida de la base de datos.
    private val _todasLasEstaciones = MutableStateFlow<List<EstacionEntity>>(emptyList())
    val todasLasEstaciones: StateFlow<List<EstacionEntity>> = _todasLasEstaciones.asStateFlow()

    // Guarda la estación seleccionada por el usuario como origen.
    private val _origenSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val origenSeleccionado: StateFlow<EstacionEntity?> = _origenSeleccionado.asStateFlow()

    // Guarda la estación seleccionada por el usuario como destino.
    private val _destinoSeleccionado = MutableStateFlow<EstacionEntity?>(null)
    val destinoSeleccionado: StateFlow<EstacionEntity?> = _destinoSeleccionado.asStateFlow()

    // Guarda el resultado del último cálculo de ruta (pasos, tiempo o error).
    private val _rutaCalculada = MutableStateFlow(RutaResultado())
    val rutaCalculada: StateFlow<RutaResultado> = _rutaCalculada.asStateFlow()

    // --- Inicialización ---

    init {
        // Al crear el ViewModel, se suscribe para obtener la lista de estaciones del repositorio.
        viewModelScope.launch {
            repository.todasLasEstaciones.collect { estaciones ->
                _todasLasEstaciones.value = estaciones
                // Si ya había una ruta calculada, la recalcula por si la lista cambió
                if (_origenSeleccionado.value != null && _destinoSeleccionado.value != null) {
                    calcularRuta()
                }
            }
        }
    }

    // --- Funciones Públicas (llamadas desde la UI) ---

    /** Actualiza la estación de origen seleccionada y recalcula la ruta. */
    fun seleccionarOrigen(estacion: EstacionEntity?) {
        // Evita recalcular si la selección no cambió
        if (estacion != _origenSeleccionado.value) {
            _origenSeleccionado.value = estacion
            calcularRuta()
        }
    }

    /** Actualiza la estación de destino seleccionada y recalcula la ruta. */
    fun seleccionarDestino(estacion: EstacionEntity?) {
        // Evita recalcular si la selección no cambió
        if (estacion != _destinoSeleccionado.value) {
            _destinoSeleccionado.value = estacion
            calcularRuta()
        }
    }

    // --- Lógica Privada ---

    /**
     * Calcula la ruta simulada basada en las estaciones de origen y destino seleccionadas.
     * Actualiza el StateFlow '_rutaCalculada' con el resultado o un mensaje de error.
     */
    private fun calcularRuta() {
        val origen = _origenSeleccionado.value
        val destino = _destinoSeleccionado.value
        val estaciones = _todasLasEstaciones.value // Usa la lista actual de estaciones

        // --- Validaciones ---
        if (origen == null || destino == null) {
            _rutaCalculada.value = RutaResultado() // Ruta vacía si falta origen o destino
            return
        }
        if (origen == destino) {
            _rutaCalculada.value = RutaResultado(mensajeError = "El origen y destino no pueden ser iguales.")
            return
        }
        if (estaciones.isEmpty()) {
            _rutaCalculada.value = RutaResultado(mensajeError = "Cargando lista de estaciones...")
            return
        }

        // --- Cálculo Simulado (asumiendo orden lineal) ---
        val indexOrigen = estaciones.indexOf(origen)
        val indexDestino = estaciones.indexOf(destino)

        // Verifica si las estaciones seleccionadas existen en la lista actual
        if (indexOrigen == -1 || indexDestino == -1) {
            _rutaCalculada.value = RutaResultado(mensajeError = "Error: Estaciones seleccionadas no válidas.")
            return
        }

        // Determina la sublista de estaciones entre origen y destino
        val pasosEstaciones = if (indexOrigen < indexDestino) {
            // Viaje en orden normal
            estaciones.subList(indexOrigen, indexDestino + 1)
        } else {
            // Viaje en orden inverso
            estaciones.subList(indexDestino, indexOrigen + 1).reversed()
        }

        // Convierte las entidades a la estructura de datos para la UI (RutaPaso)
        val pasosRuta = pasosEstaciones.map { RutaPaso(it.nombre) }

        // Simula el tiempo estimado (ej: 3 minutos por tramo entre estaciones)
        val tiempoEstimado = (pasosRuta.size - 1) * 3

        // Actualiza el StateFlow con el resultado exitoso
        _rutaCalculada.value = RutaResultado(pasos = pasosRuta, tiempoEstimadoMinutos = tiempoEstimado)
    }

    // --- Fábrica para la Inyección de Dependencias ---
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                PlanificadorRutaViewModel(application.repository)
            }
        }
    }
}
