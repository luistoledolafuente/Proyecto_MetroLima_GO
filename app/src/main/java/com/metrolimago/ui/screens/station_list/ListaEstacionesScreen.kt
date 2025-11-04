package com.metrolimago.ui.screens.station_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metrolimago.data.model.EstacionEntity

@Composable
fun ListaEstacionesScreen(
    onStationClick: (String) -> Unit
) {
    val estaciones = listOf(
        EstacionEntity(1, "Gamarra", "1", "La Victoria", -12.066, -77.02, "05:00-23:00"),
        EstacionEntity(2, "Arriola", "1", "La Victoria", -12.065, -77.025, "05:00-23:00")
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(estaciones) { estacion ->
            Text(
                text = "${estacion.nombre} - Línea ${estacion.linea}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStationClick(estacion.nombre) }
                    .padding(16.dp)
            )
        }
    }
}
