package com.metrolimago.ui.screens.station_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metrolimago.data.model.EstacionEntity

@Composable
fun ListaEstacionesScreen(
    onStationClick: (String) -> Unit,
    viewModel: ListaEstacionesViewModel = viewModel(factory = ListaEstacionesViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra de Búsqueda
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            label = { Text("Buscar estación...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Estaciones
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.estaciones) { estacion ->
                EstacionItem(estacion = estacion, onClick = onStationClick)
            }
        }
    }
}

@Composable
fun EstacionItem(
    estacion: EstacionEntity,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(estacion.nombre) } // Usa el ID o nombre
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = estacion.nombre, style = MaterialTheme.typography.bodyLarge)
            // Aquí podrías poner el ícono de la línea
        }
    }
}