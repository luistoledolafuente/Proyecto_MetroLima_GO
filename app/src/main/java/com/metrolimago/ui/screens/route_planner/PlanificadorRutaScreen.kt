package com.metrolimago.ui.screens.route_planner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.ui.theme.MetroLimaGOTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorRutaScreen(
    onBackClick: () -> Unit,
    planificadorViewModel: PlanificadorRutaViewModel = viewModel(factory = PlanificadorRutaViewModel.Factory)
) {
    val todasLasEstaciones by planificadorViewModel.todasLasEstaciones.collectAsState()
    val origenSeleccionado by planificadorViewModel.origenSeleccionado.collectAsState()
    val destinoSeleccionado by planificadorViewModel.destinoSeleccionado.collectAsState()
    val rutaCalculada by planificadorViewModel.rutaCalculada.collectAsState()

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificar Ruta") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.surface)
            )
        },
        containerColor = colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SelectorEstacion(
                label = "Origen",
                estacionSeleccionada = origenSeleccionado,
                estacionesDisponibles = todasLasEstaciones,
                onEstacionSelected = { planificadorViewModel.seleccionarOrigen(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Intercambiar Origen/Destino",
                tint = colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectorEstacion(
                label = "Destino",
                estacionSeleccionada = destinoSeleccionado,
                estacionesDisponibles = todasLasEstaciones,
                onEstacionSelected = { planificadorViewModel.seleccionarDestino(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ResultadosRutaSection(ruta = rutaCalculada)
        }
    }
}

@Composable
private fun ResultadosRutaSection(ruta: RutaResultado) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    when {
        ruta.mensajeError != null -> {
            Text(
                ruta.mensajeError,
                color = colorScheme.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        ruta.pasos.isNotEmpty() -> {
            Text("Ruta Sugerida:", style = typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tiempo estimado: ${ruta.tiempoEstimadoMinutos} minutos", color = colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorScheme.surfaceVariant)
            ) {
                itemsIndexed(ruta.pasos) { index, paso ->
                    PasoRutaItem(
                        paso = paso,
                        esInicio = index == 0,
                        esFin = index == ruta.pasos.size - 1
                    )
                    if (index < ruta.pasos.size - 1) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }

        else -> {
            Text(
                "Selecciona un origen y destino para calcular la ruta.",
                modifier = Modifier.padding(vertical = 16.dp),
                color = colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorEstacion(
    label: String,
    estacionSeleccionada: EstacionEntity?,
    estacionesDisponibles: List<EstacionEntity>,
    onEstacionSelected: (EstacionEntity?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val colorScheme = MaterialTheme.colorScheme

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = estacionSeleccionada?.nombre ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.outline,
                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Ninguna") },
                onClick = {
                    onEstacionSelected(null)
                    expanded = false
                }
            )
            estacionesDisponibles.forEach { estacion ->
                DropdownMenuItem(
                    text = { Text(estacion.nombre) },
                    onClick = {
                        onEstacionSelected(estacion)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun PasoRutaItem(paso: RutaPaso, esInicio: Boolean, esFin: Boolean) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when {
                esInicio -> Icons.Default.TripOrigin
                esFin -> Icons.Default.CheckCircle
                else -> Icons.Default.Train
            },
            contentDescription = null,
            tint = if (esInicio || esFin) colorScheme.primary else colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = paso.nombreEstacion,
            fontWeight = if (esInicio || esFin) FontWeight.Bold else FontWeight.Normal,
            color = if (esInicio || esFin) colorScheme.onSurface else colorScheme.onSurfaceVariant
        )
    }
}

// --- PREVIEWS ---
@Preview(showBackground = true, name = "Selector Estación")
@Composable
fun SelectorEstacionPreview() {
    val estaciones = listOf(
        EstacionEntity(1, "Gamarra", 1, "La Victoria"),
        EstacionEntity(2, "Arriola", 1, "La Victoria")
    )
    var seleccion: EstacionEntity? by remember { mutableStateOf(estaciones[0]) }

    MetroLimaGOTheme {
        Column(Modifier.padding(16.dp)) {
            SelectorEstacion("Origen", seleccion, estaciones) { seleccion = it }
        }
    }
}

@Preview(showBackground = true, name = "Paso de Ruta")
@Composable
fun PasoRutaItemPreview() {
    MetroLimaGOTheme {
        Column {
            PasoRutaItem(paso = RutaPaso("Estación Inicial"), esInicio = true, esFin = false)
            Divider()
            PasoRutaItem(paso = RutaPaso("Estación Intermedia"), esInicio = false, esFin = false)
            Divider()
            PasoRutaItem(paso = RutaPaso("Estación Final"), esInicio = false, esFin = true)
        }
    }
}
