package com.metrolimago.ui.screens.station_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
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

// Tarea 2: Crear Datos Falsos (Mock) con estructura para la UI de Figma
data class Station(
    val name: String,
    val line: Int,
    val district: String
)

val mockStations = listOf(
    Station("Villa El Salvador", 1, "Villa El Salvador"),
    Station("Parque Industrial", 1, "Villa El Salvador"),
    Station("Villa María del Triunfo", 1, "Villa María del Triunfo"),
    Station("Puente Atocongo", 1, "San Juan de Miraflores"),
    Station("Jorge Chávez", 1, "Santiago de Surco"),
    Station("Ayacucho", 1, "Santiago de Surco"),
    Station("Cabitos", 1, "San Juan de Miraflores"),
    Station("San Juan", 1, "San Juan de Miraflores"),
    Station("Atocongo", 1, "San Juan de Miraflores")
    // ... puedes añadir más estaciones aquí
)

// Colores del diseño
val PurpleHeader = Color(0xFF6A3DE8)
val Line1Green = Color(0xFF2EBD85)
val Line2Yellow = Color(0xFFF7C325)
val ScreenBackground = Color(0xFFF5F5F5)
val LightGrayText = Color(0xFF8A8A8A)

/**
 * Tarea 1 y 3: Pantalla completa basada en el diseño de Figma.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionesScreen(
    // Tarea 4: Preparar la Navegación
    onStationClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estaciones", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PurpleHeader)
            )
        },
        containerColor = ScreenBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header con Buscador y Filtros
            item {
                HeaderSection()
            }

            // Contador de Estaciones
            item {
                Text(
                    text = "${mockStations.size} estaciones",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    color = LightGrayText,
                    fontSize = 14.sp
                )
            }

            // Lista de Estaciones
            items(mockStations) { station ->
                StationListItem(
                    station = station,
                    onItemClick = { onStationClick(station.name) }
                )
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PurpleHeader)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra de búsqueda (visual)
        var searchText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Buscar estación...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de filtro (visuales)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { /* Lógica futura */ }, colors = ButtonDefaults.buttonColors(containerColor = Color.White), modifier = Modifier.weight(1f)) {
                Text("Todas", color = PurpleHeader)
            }
            Button(onClick = { /* Lógica futura */ }, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))) {
                Box(modifier = Modifier.size(8.dp).background(Line1Green, CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Línea 1", color = Color.White)
            }
            Button(onClick = { /* Lógica futura */ }, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))) {
                Box(modifier = Modifier.size(8.dp).background(Line2Yellow, CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Línea 2", color = Color.White)
            }
        }
    }
}

/**
 * Composable reutilizable para cada fila de la lista, fiel a Figma.
 */
@Composable
fun StationListItem(
    station: Station,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            // Barra lateral de color
            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .height(50.dp)
                    .background(Line1Green)
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Textos
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = station.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Línea ${station.line}",
                        color = Line1Green,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(Line1Green.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = station.district,
                        color = LightGrayText,
                        fontSize = 14.sp
                    )
                }
            }

            // Flecha a la derecha
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ver detalles",
                tint = LightGrayText,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaEstacionesScreenPreview() {
    ListaEstacionesScreen(onStationClick = { stationName ->
        println("Clicked on: $stationName")
    })
}