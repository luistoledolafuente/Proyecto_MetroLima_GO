package com.metrolimago.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metrolimago.data.model.Alerta
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// --- Estados posibles de la UI ---
sealed interface HomeUiState {
    object Loading : HomeUiState // Estado inicial o mientras carga
    data class Success(val alertas: List<Alerta>) : HomeUiState // Cuando los datos llegan bien
    data class Error(val message: String) : HomeUiState // Si ocurre un error
}

class HomeViewModel(private val repository: MetroRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        cargarAlertas()
    }

    fun cargarAlertas() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.obtenerAlertasDesdeApi()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = HomeUiState.Success(response.body()!!)
                } else {
                    _uiState.value = HomeUiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("Error de red. Verifica tu conexión.")
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Error inesperado: ${e.message}")
            }
        }
    }
}
