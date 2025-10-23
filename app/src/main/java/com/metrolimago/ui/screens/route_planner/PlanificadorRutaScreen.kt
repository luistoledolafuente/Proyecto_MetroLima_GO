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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.ui.theme.* // Importa tus colores del tema

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanificadorRutaScreen(
    onBackClick: () -> Unit,
    // Conexión final al ViewModel usando la Factory
    planificadorViewModel: PlanificadorRutaViewModel = viewModel(factory = PlanificadorRutaViewModel.Factory)
) {
    // Observa los datos del ViewModel
    val todasLasEstaciones by planificadorViewModel.todasLasEstaciones.collectAsState()
    val origenSeleccionado by planificadorViewModel.origenSeleccionado.collectAsState()
    val destinoSeleccionado by planificadorViewModel.destinoSeleccionado.collectAsState()
    val rutaCalculada by planificadorViewModel.rutaCalculada.collectAsState()

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
            // --- Selectores ---
            SelectorEstacion(
                label = "Origen",
                estacionSeleccionada = origenSeleccionado,
                estacionesDisponibles = todasLasEstaciones,
                onEstacionSelected = { planificadorViewModel.seleccionarOrigen(it) } // Llama al ViewModel
            )

            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Intercambiar Origen/Destino",
                tint = MetroLimaPurple,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            SelectorEstacion(
                label = "Destino",
                estacionSeleccionada = destinoSeleccionado,
                estacionesDisponibles = todasLasEstaciones,
                onEstacionSelected = { planificadorViewModel.seleccionarDestino(it) } // Llama al ViewModel
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Resultados de la Ruta ---
            ResultadosRutaSection(ruta = rutaCalculada)
        }
    }
}

/**
 * Sección que muestra los resultados del cálculo de la ruta.
 */
@Composable
private fun ResultadosRutaSection(ruta: RutaResultado) {
    // Muestra error si existe
    if (ruta.mensajeError != null) {
        Text(ruta.mensajeError, color = Color.Red, modifier = Modifier.padding(vertical = 16.dp))
    }
    // Muestra la ruta si hay pasos calculados
    else if (ruta.pasos.isNotEmpty()) {
        Text("Ruta Sugerida:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Tiempo estimado: ${ruta.tiempoEstimadoMinutos} minutos", color = TextSecondary)
        Spacer(modifier = Modifier.height(16.dp))

        // Usamos LazyColumn por si la ruta es muy larga
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(CardBackground)
        ) {
            itemsIndexed(ruta.pasos) { index, paso ->
                PasoRutaItem(
                    paso = paso,
                    esInicio = index == 0,
                    esFin = index == ruta.pasos.size - 1
                )
                // Añade un divisor entre pasos, excepto después del último
                if (index < ruta.pasos.size - 1) {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
    // Mensaje por defecto si no hay origen/destino o la ruta está vacía
    else {
        Text(
            "Selecciona un origen y destino para calcular la ruta.",
            modifier = Modifier.padding(vertical = 16.dp),
            color = TextSecondary
        )
    }
}

/**
 * Composable reutilizable para el selector de estación con Dropdown.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorEstacion(
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
            onValueChange = {}, // El campo no es editable directamente
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor() // Necesario para vincular el menú al campo
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            colors = OutlinedTextFieldDefaults.colors( // Colores estándar
                focusedBorderColor = MetroLimaPurple,
                unfocusedBorderColor = DisabledGray
            )
        )
        // Menú desplegable
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Cierra el menú si se hace clic fuera
        ) {
            // Opción para limpiar la selección
            DropdownMenuItem(
                text = { Text("Ninguna") },
                onClick = {
                    onEstacionSelected(null)
                    expanded = false
                }
            )
            // Opciones para cada estación disponible
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
 * Composable reutilizable para mostrar un paso individual de la ruta.
 */
@Composable
private fun PasoRutaItem(paso: RutaPaso, esInicio: Boolean, esFin: Boolean) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono representativo del paso (origen, intermedio, fin)
        Icon(
            imageVector = when {
                esInicio -> Icons.Default.TripOrigin
                esFin -> Icons.Default.CheckCircle
                else -> Icons.Default.Train // Icono para estaciones intermedias
            },
            contentDescription = null, // Descripción no necesaria para icono decorativo
            tint = if (esInicio || esFin) MetroLimaPurple else TextSecondary // Color distintivo
        )
        Spacer(modifier = Modifier.width(16.dp))
        // Nombre de la estación
        Text(
            text = paso.nombreEstacion,
            fontWeight = if (esInicio || esFin) FontWeight.Bold else FontWeight.Normal, // Resalta origen/fin
            color = if (esInicio || esFin) TextPrimary else TextSecondary
        )
    }
}

// --- PREVIEW ---
// El Preview principal es complejo por el ViewModel.
// Es más útil previsualizar los componentes pequeños individualmente.
@Preview(showBackground = true, name = "Selector Estación")
@Composable
fun SelectorEstacionPreview() {
    val estaciones = listOf(EstacionEntity(1, "Gamarra", 1, "La Victoria"), EstacionEntity(2, "Arriola", 1, "La Victoria"))
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
