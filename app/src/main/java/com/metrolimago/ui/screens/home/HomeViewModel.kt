package com.metrolimago.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.metrolimago.MetroLimaApp
import com.metrolimago.data.model.Alerta
import com.metrolimago.data.repository.MetroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val alertas: List<Alerta>) : HomeUiState
    data class Error(val message: String) : HomeUiState
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
                val response: Response<List<Alerta>> = repository.syncAlertas()
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MetroLimaApp)
                HomeViewModel(app.repository)
            }
        }
    }
}
