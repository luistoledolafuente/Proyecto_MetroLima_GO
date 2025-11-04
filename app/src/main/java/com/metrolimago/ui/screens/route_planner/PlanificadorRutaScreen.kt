package com.metrolimago.ui.screens.route_planner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metrolimago.MetroLimaApp
import com.metrolimago.data.model.EstacionEntity

@Composable
fun PlanificadorRutaScreen(
    onBackClick: () -> Unit, // Para el botón de "atrás"
    // El ViewModel ahora se inyectará correctamente gracias al Paso 1
    viewModel: PlanificadorRutaViewModel = viewModel(
        factory = PlanificadorRutaViewModel.provideFactory(
            (LocalContext.current.applicationContext as MetroLimaApp).repository
        )
    )
) {
    val estaciones by viewModel.todasLasEstaciones.collectAsState()
    val origen by viewModel.origenSeleccionado.collectAsState()
    val destino by viewModel.destinoSeleccionado.collectAsState()
    val ruta by viewModel.rutaCalculada.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // (Aquí iría tu TopAppBar con el onBackClick)
        Text(text = "Planificar Ruta", style = MaterialTheme.typography.headlineSmall)

        // Selector de Origen
        EstacionSelector(
            label = "Desde (Origen)",
            estaciones = estaciones,
            selected = origen,
            onSelected = viewModel::seleccionarOrigen
        )

        // Selector de Destino
        EstacionSelector(
            label = "Hasta (Destino)",
            estaciones = estaciones,
            selected = destino,
            onSelected = viewModel::seleccionarDestino
        )

        Button(
            onClick = { /* la ruta se calcula automáticamente */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = origen != null && destino != null
        ) {
            Text("Calcular Ruta")
        }

        // Mostrar resultado (simple)
        if (ruta.pasos.isNotEmpty()) {
            Text("Tiempo estimado: ${ruta.tiempoEstimadoMinutos} min.")
        }
    }
}

// Composable reutilizable para el Dropdown
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstacionSelector(
    label: String,
    estaciones: List<EstacionEntity>,
    selected: EstacionEntity?,
    onSelected: (EstacionEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected?.nombre ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            estaciones.forEach { estacion ->
                DropdownMenuItem(
                    text = { Text(estacion.nombre) },
                    onClick = {
                        onSelected(estacion)
                        expanded = false
                    }
                )
            }
        }
    }
}