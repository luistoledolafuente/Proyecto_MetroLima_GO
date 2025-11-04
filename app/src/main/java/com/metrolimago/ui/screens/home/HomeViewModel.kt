package com.metrolimago.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer // <-- AÑADIDO
import androidx.lifecycle.viewmodel.viewModelFactory // <-- AÑADIDO
import com.metrolimago.MetroLimaApp // <-- AÑADIDO
import com.metrolimago.data.model.Alerta
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response // <-- AÑADIDO
import java.io.IOException // <-- AÑADIDO

/**
 * sealed interface para manejar los estados de la UI de HomeScreen
 */
sealed interface HomeUiState {
    data class Success(val alertas: List<Alerta>) : HomeUiState
    data class Error(val mensaje: String) : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repository: MetroRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarAlertas()
    }

    fun cargarAlertas() {
        _uiState.value = HomeUiState.Loading

        viewModelScope.launch {
            try {
                // Aquí es donde se llama a la función suspendida
                val response: Response<List<Alerta>> = repository.syncAlertas()

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = HomeUiState.Success(response.body()!!)
                } else {
                    _uiState.value = HomeUiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: IOException) {
                // Manejo de errores de red (ej. sin conexión)
                _uiState.value = HomeUiState.Error("Error de red. Verifica tu conexión.")
            } catch (e: Exception) {
                // Manejo de otros errores
                _uiState.value = HomeUiState.Error("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Factory para crear el HomeViewModel con su dependencia (MetroRepository)
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                HomeViewModel(app.repository)
            }
        }
    }
}