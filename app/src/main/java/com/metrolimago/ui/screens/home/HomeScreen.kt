package com.metrolimago.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToStationList: () -> Unit,
    onNavigateToRoutePlanner: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onNavigateToStationList, modifier = Modifier.fillMaxWidth()) {
            Text("Lista de Estaciones")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToRoutePlanner, modifier = Modifier.fillMaxWidth()) {
            Text("Planificador de Ruta")
        }
    }
}
