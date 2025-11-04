package com.metrolimago.ui.screens.station_list // Paquete correcto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.metrolimago.MetroLimaApp
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.*

data class ListaEstacionesUiState(
    val estaciones: List<EstacionEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

// Nombre de clase correcto
class ListaEstacionesViewModel(private val repository: MetroRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _todasLasEstacionesFlow = repository.getEstaciones()

    val uiState: StateFlow<ListaEstacionesUiState> =
        combine(_searchQuery, _todasLasEstacionesFlow) { query, estaciones ->
            val estacionesFiltradas = if (query.isBlank()) {
                estaciones
            } else {
                estaciones.filter {
                    it.nombre.contains(query, ignoreCase = true)
                }
            }
            ListaEstacionesUiState(
                estaciones = estacionesFiltradas,
                searchQuery = query,
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ListaEstacionesUiState(isLoading = true)
        )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    // Factory correcto
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                ListaEstacionesViewModel(app.repository)
            }
        }
    }
}