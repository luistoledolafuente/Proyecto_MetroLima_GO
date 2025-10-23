package com.metrolimago.ui.screens.route_planner


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.TripOrigin // Asegúrate de importar este
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import androidx.lifecycle.viewmodel.compose.viewModel // Se usará en Fase 3
import com.metrolimago.data.model.EstacionEntity // Importar EstacionEntity
import com.metrolimago.ui.theme.* // Tus colores

// --- DEFINICIONES TEMPORALES (de Tarea 1.1 de Carlos) ---
// Añadidas aquí para que el archivo compile mientras Luis no termina la Fase 3
data class RutaPaso(val nombreEstacion: String)

data class RutaResultado(
    val pasos: List<RutaPaso> = emptyList(),
    val tiempoEstimadoMinutos: Int = 0,
    val mensajeError: String? = null
)
// -----------------------------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorRutaScreen(
    onBackClick: () -> Unit,
    // La conexión final con la Factory la hará Luis
    // planificadorViewModel: PlanificadorRutaViewModel = viewModel(...) // <-- Se añadirá después
) {
    // --- CONEXIÓN TEMPORAL (SOLO PARA QUE COMPILE Y VEAS EL DISEÑO) ---
    // Simula el ViewModel para el Preview y para que compile
    val todasLasEstaciones = listOf(
        EstacionEntity(1, "Estación A", 1, "Distrito X"),
        EstacionEntity(2, "Estación B", 1, "Distrito X"),
        EstacionEntity(3, "Estación C", 1, "Distrito Y")
    )
    val origenSeleccionado: EstacionEntity? = todasLasEstaciones[0]
    val destinoSeleccionado: EstacionEntity? = todasLasEstaciones[2]
    val rutaCalculada = RutaResultado(
        pasos = listOf(RutaPaso("Estación A"), RutaPaso("Estación B"), RutaPaso("Estación C")),
        tiempoEstimadoMinutos = 6
    )
    // Funciones de placeholder
    val seleccionarOrigen: (EstacionEntity?) -> Unit = {}
    val seleccionarDestino: (EstacionEntity?) -> Unit = {}

    // Colores de ejemplo (ajusta a tu ui.theme/Color.kt si es necesario)
    val BackgroundLight = Color(0xFFF5F5F5)
    val MetroLimaPurple = Color(0xFF6A3DE8)
    val TextSecondary = Color.Gray
    val TextPrimary = Color.Black
    val CardBackground = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificar Ruta") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Selectores de Origen y Destino
            SelectorEstacion(
                label = "Origen",
                estacionSeleccionada = origenSeleccionado,
                estacionesDisponibles = todasLasEstaciones,
                onEstacionSelected = seleccionarOrigen
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Icono para intercambiar (solo visual por ahora)
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Intercambiar Origen/Destino",
                tint = MetroLimaPurple,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            SelectorEstacion(
                label = "Destino",
                estacionSeleccionada = destinoSeleccionado,
                estacionesDisponibles = todasLasEstaciones,
                onEstacionSelected = seleccionarDestino
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sección de Resultados
            if (rutaCalculada.mensajeError != null) {
                // Mostrar mensaje de error si existe
                Text(rutaCalculada.mensajeError, color = Color.Red)
            } else if (rutaCalculada.pasos.isNotEmpty()) {
                // Mostrar la ruta si hay pasos
                Text("Ruta Sugerida:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Tiempo estimado: ${rutaCalculada.tiempoEstimadoMinutos} minutos", color = TextSecondary)
                Spacer(modifier = Modifier.height(16.dp))
                // Lista de pasos (usando LazyColumn por si son muchos)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBackground)
                ) {
                    itemsIndexed(rutaCalculada.pasos) { index, paso ->
                        PasoRutaItem(
                            paso = paso,
                            esInicio = index == 0,
                            esFin = index == rutaCalculada.pasos.size - 1
                        )
                        if (index < rutaCalculada.pasos.size - 1) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            } else {
                // Mensaje inicial o si no hay ruta válida
                Text("Selecciona un origen y destino para calcular la ruta.")
            }
        }
    }
}

/**
 * Composable para el selector de estación con Dropdown.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorEstacion(
    label: String,
    estacionSeleccionada: EstacionEntity?,
    estacionesDisponibles: List<EstacionEntity>,
    onEstacionSelected: (EstacionEntity?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = estacionSeleccionada?.nombre ?: "",
            onValueChange = {}, // No editable directamente
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor() // Necesario para el dropdown
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Opción para deseleccionar
            DropdownMenuItem(
                text = { Text("Ninguna") },
                onClick = {
                    onEstacionSelected(null)
                    expanded = false
                }
            )
            // Lista de estaciones disponibles
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

/**
 * Composable para mostrar un paso de la ruta.
 */
@Composable
fun PasoRutaItem(paso: RutaPaso, esInicio: Boolean, esFin: Boolean) {
    // Colores de ejemplo (ajusta a tu ui.theme/Color.kt si es necesario)
    val MetroLimaPurple = Color(0xFF6A3DE8)
    val TextSecondary = Color.Gray
    val TextPrimary = Color.Black

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
            tint = if (esInicio || esFin) MetroLimaPurple else TextSecondary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = paso.nombreEstacion,
            fontWeight = if (esInicio || esFin) FontWeight.Bold else FontWeight.Normal,
            color = if (esInicio || esFin) TextPrimary else TextSecondary
        )
    }
}


// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun PlanificadorRutaScreenPreview() {
    MetroLimaGOTheme {
        PlanificadorRutaScreen(onBackClick = {})
    }
}