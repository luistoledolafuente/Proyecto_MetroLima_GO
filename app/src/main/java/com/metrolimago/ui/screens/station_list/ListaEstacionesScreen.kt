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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEstacionesScreen(
    onStationClick: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val filteredStations = if (searchText.isBlank()) {
        mockStations
    } else {
        mockStations.filter {
            it.name.contains(searchText, ignoreCase = true) ||
                    it.district.contains(searchText, ignoreCase = true)
        }
    }

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estaciones", color = colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.primary)
            )
        },
        containerColor = colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HeaderSection(
                searchText = searchText,
                onSearchTextChange = { newText -> searchText = newText }
            )

            LazyColumn {
                item {
                    Text(
                        text = "${filteredStations.size} estaciones encontradas",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        color = colorScheme.onSurfaceVariant,
                        style = typography.bodySmall
                    )
                }

                items(filteredStations) { station ->
                    StationListItem(
                        station = station,
                        onItemClick = { onStationClick(station.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.primary)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            placeholder = { Text("Buscar por nombre o distrito...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                focusedIndicatorColor = colorScheme.primary,
                unfocusedIndicatorColor = colorScheme.outline
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(text = "Todas", modifier = Modifier.weight(1f))
            FilterButton(text = "Línea 1", color = colorScheme.tertiary, modifier = Modifier.weight(1f))
            FilterButton(text = "Línea 2", color = colorScheme.secondary, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    color: androidx.compose.ui.graphics.Color? = null,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Button(
        onClick = { /* lógica futura */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = color ?: colorScheme.surface,
            contentColor = if (color != null) colorScheme.onPrimary else colorScheme.onSurface
        ),
        modifier = modifier
    ) {
        if (color != null) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text)
    }
}

@Composable
fun StationListItem(
    station: Station,
    onItemClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onItemClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .height(50.dp)
                    .background(colorScheme.tertiary)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = station.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Línea ${station.line}",
                        color = colorScheme.tertiary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                colorScheme.tertiary.copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = station.district,
                        color = colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ver detalles",
                tint = colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaEstacionesScreenPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        ListaEstacionesScreen(onStationClick = {})
    }
}
