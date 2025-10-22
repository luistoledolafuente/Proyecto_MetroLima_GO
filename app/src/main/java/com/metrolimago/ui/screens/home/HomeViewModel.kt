package com.metrolimago.ui.screens.home

// Imports necesarios para ViewModel, Coroutines y StateFlow
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// Imports necesarios para la Fábrica (Factory)
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.metrolimago.MetroLimaApp // Importar la clase Application

// Imports del modelo de datos y el repositorio
import com.metrolimago.data.model.Alerta
import com.metrolimago.data.repository.MetroRepository

// --- Estados posibles de la UI ---
sealed interface HomeUiState {
    object Loading : HomeUiState // Estado inicial o mientras carga
    data class Success(val alertas: List<Alerta>) : HomeUiState // Cuando los datos llegan bien
    data class Error(val message: String) : HomeUiState // Si ocurre un error
}

class HomeViewModel(private val repository: MetroRepository) : ViewModel() {

    // StateFlow privado para modificar el estado
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    // StateFlow público para que la UI observe los cambios
    val uiState: StateFlow<HomeUiState> = _uiState

    // El bloque init se ejecuta al crear el ViewModel
    init {
        cargarAlertas() // Llama a la función para buscar las alertas
    }

    // Función para obtener las alertas desde el repositorio
    fun cargarAlertas() {
        _uiState.value = HomeUiState.Loading
        Log.d("HomeViewModel", "Estado: Loading") // Log inicial
        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Intentando obtener alertas desde la API...") // Log antes de la llamada
                val response = repository.obtenerAlertasDesdeApi()
                if (response.isSuccessful && response.body() != null) {
                    Log.d("HomeViewModel", "Éxito: Datos recibidos (${response.body()!!.size} alertas)") // Log de éxito
                    _uiState.value = HomeUiState.Success(response.body()!!)
                } else {
                    Log.e("HomeViewModel", "Error en respuesta API: Código ${response.code()} - ${response.message()}") // Log de error API
                    _uiState.value = HomeUiState.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("HomeViewModel", "Error de RED (IOException)", e) // Log de error de red
                _uiState.value = HomeUiState.Error("Error de red. Verifica tu conexión.")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error INESPERADO", e) // Log de otro error
                _uiState.value = HomeUiState.Error("Error inesperado: ${e.message}")
            }
        }
    }

    // --- FÁBRICA PARA CREAR EL VIEWMODEL ---
    // Este bloque (companion object) es la Tarea 3.2 que tú, Luis, debes añadir.
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Obtenemos la instancia de nuestra MetroLimaApp
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                // Le pasamos el repositorio de la aplicación al ViewModel
                HomeViewModel(application.repository)
            }
        }
    }
}
