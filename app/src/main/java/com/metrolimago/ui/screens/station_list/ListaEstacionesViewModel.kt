package com.metrolimago.ui.screens.station_list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.metrolimago.MetroLimaApp
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.*

class ListaEstacionesViewModel(private val repository: MetroRepository) : ViewModel() {

    private var _listaCompleta = listOf<EstacionEntity>()
    private val _estacionesMostradas = MutableStateFlow<List<EstacionEntity>>(emptyList())
    val estacionesMostradas: StateFlow<List<EstacionEntity>> = _estacionesMostradas

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    init {
        repository.todasLasEstaciones
            .onEach { estaciones ->
                _listaCompleta = estaciones
                if (_searchText.value.isBlank()) {
                    _estacionesMostradas.value = estaciones
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

    // Fábrica para crear el ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                ListaEstacionesViewModel(application.repository)
            }
        }
    }
}