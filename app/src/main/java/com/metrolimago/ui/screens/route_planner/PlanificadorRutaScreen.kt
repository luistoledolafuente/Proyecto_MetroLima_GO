package com.metrolimago.ui.screens.route_planner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // <-- AÑADIDO
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metrolimago.MetroLimaApp // <-- AÑADIDO
// import com.metrolimago.data.repository.PlanificadorRutaRepository // <-- ELIMINADO

@Composable
fun PlanificadorRutaScreen(
    onBackClick: () -> Unit
) {
    // --- CORRECCIÓN ---
    // Obtenemos el repositorio unificado desde la Aplicación
    val context = LocalContext.current
    val app = context.applicationContext as MetroLimaApp
    val metroRepository = app.repository

    // Creamos el ViewModel usando el factory corregido que espera un MetroRepository
    val viewModel: PlanificadorRutaViewModel = viewModel(
        factory = PlanificadorRutaViewModel.provideFactory(metroRepository)
    )
    // --- FIN DE LA CORRECCIÓN ---

    val origen by viewModel.origenSeleccionado.collectAsState()
    val destino by viewModel.destinoSeleccionado.collectAsState()
    val estaciones by viewModel.todasLasEstaciones.collectAsState()
    val ruta by viewModel.rutaCalculada.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Planificador de Rutas")
        Spacer(modifier = Modifier.height(16.dp))

        // (Aquí iría tu UI para seleccionar origen y destino)
        Text("Origen: ${origen?.nombre ?: "No seleccionado"}")
        Text("Destino: ${destino?.nombre ?: "No seleccionado"}")

        // Botones de ejemplo para simular la selección
        Row {
            Button(onClick = {
                if (estaciones.isNotEmpty()) viewModel.seleccionarOrigen(estaciones.first())
            }) {
                Text("Sel. Origen (Test)")
            }
            Button(onClick = {
                if (estaciones.size > 1) viewModel.seleccionarDestino(estaciones.last())
            }) {
                Text("Sel. Destino (Test)")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultado de la ruta
        if (ruta.pasos.isNotEmpty()) {
            Text("Ruta Calculada (${ruta.tiempoEstimadoMinutos} min):")
            ruta.pasos.forEach { paso ->
                Text("- ${paso.nombreEstacion}")
            }
        } else {
            Text("Selecciona un origen y destino.")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBackClick) {
            Text("Volver")
        }
    }
}