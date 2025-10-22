package com.metrolimago.ui.screens.station_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.metrolimago.MetroLimaApp // Asegúrate que este import esté correcto
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.*

class ListaEstacionesViewModel(private val repository: MetroRepository) : ViewModel() {

    private var _listaCompleta = listOf<EstacionEntity>()
    private val _estacionesMostradas = MutableStateFlow<List<EstacionEntity>>(emptyList())
    val estacionesMostradas: StateFlow<List<EstacionEntity>> = _estacionesMostradas.asStateFlow() // Usa asStateFlow() para la versión pública

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow() // Usa asStateFlow()

    init {
        repository.todasLasEstaciones
            .onEach { estaciones ->
                _listaCompleta = estaciones
                // Aplica el filtro actual cada vez que la lista completa cambia
                val textoActual = _searchText.value
                _estacionesMostradas.value = if (textoActual.isBlank()) {
                    estaciones
                } else {
                    _listaCompleta.filter { estacion ->
                        estacion.nombre.contains(textoActual, ignoreCase = true)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        _estacionesMostradas.value = _listaCompleta.filter { estacion ->
            estacion.nombre.contains(text, ignoreCase = true)
        }
    }

    // --- FÁBRICA VERIFICADA ---
    // Esta es la Fábrica correcta que usa application.repository
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                ListaEstacionesViewModel(application.repository)
            }
        }
    }
}

