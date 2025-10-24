package com.metrolimago.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metrolimago.R
import com.metrolimago.data.model.Alerta
import com.metrolimago.ui.theme.*

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    onStationClick: (String) -> Unit = {}
) {
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreenContent(uiState = uiState, onStationClick = onStationClick)
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onStationClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(32.dp)) }

        // --- Sección del Logo ---
        item {
            Text(
                text = "Bienvenido a",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo_metrolima_go),
                    contentDescription = "Logo MetroLima GO",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "MetroLima\nGO",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- Estado del Sistema ---
        item {
            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(vertical = 24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is HomeUiState.Success -> {
                    val alerta = uiState.alertas.firstOrNull()
                    EstadoSistemaCard(
                        alerta = alerta ?: Alerta(
                            0,
                            "Todos los sistemas operativos",
                            "Servicio normal",
                            "INFO"
                        )
                    )
                }

                is HomeUiState.Error -> {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = uiState.message,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- Tarjeta de Estaciones Cercanas ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column {
                    // Cabecera
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estaciones Cercanas",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        TextButton(onClick = { /* TODO: Implementar lógica de ubicación */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ubicarme),
                                contentDescription = "Ubicarme",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Ubicarme",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    // Contenido inferior
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(
                                    bottomStart = 20.dp,
                                    bottomEnd = 20.dp
                                )
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Placeholder del mapa
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "GRÁFICO DEL MAPA AQUÍ",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            StationItem("1", "Grau", "~1.4 km") { onStationClick("Grau") }
                            StationItem("2", "Gamarra", "~5.3 km") { onStationClick("Gamarra") }
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun EstadoSistemaCard(alerta: Alerta) {
    val (icon, color) = when (alerta.tipo.uppercase()) {
        "INFO" -> Icons.Default.CheckCircle to MetroLimaGreen
        "WARNING", "ADVERTENCIA" -> Icons.Default.Warning to MaterialTheme.colorScheme.tertiary
        "ERROR", "PELIGRO" -> Icons.Default.Error to MaterialTheme.colorScheme.error
        else -> Icons.Default.Info to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Estado",
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = alerta.titulo,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                )
                if (alerta.mensaje.isNotBlank()) {
                    Text(
                        text = alerta.mensaje,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun StationItem(number: String, name: String, distance: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Distancia aprox.: $distance",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Ubicación",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

// --- PREVIEWS ---
@Preview(showBackground = true, name = "HomeScreen Loading")
@Composable
fun HomeScreenLoadingPreview() {
    MetroLimaGOTheme { HomeScreenContent(uiState = HomeUiState.Loading, onStationClick = {}) }
}

@Preview(showBackground = true, name = "HomeScreen Success - Info")
@Composable
fun HomeScreenSuccessInfoPreview() {
    MetroLimaGOTheme {
        val alerta = Alerta(1, "Servicio normal", "Operando con normalidad.", "INFO")
        HomeScreenContent(uiState = HomeUiState.Success(listOf(alerta)), onStationClick = {})
    }
}

@Preview(showBackground = true, name = "HomeScreen Success - Warning")
@Composable
fun HomeScreenSuccessWarningPreview() {
    MetroLimaGOTheme {
        val alerta = Alerta(2, "Demora en Línea 1", "Incidente menor en estación X.", "ADVERTENCIA")
        HomeScreenContent(uiState = HomeUiState.Success(listOf(alerta)), onStationClick = {})
    }
}

@Preview(showBackground = true, name = "HomeScreen Error")
@Composable
fun HomeScreenErrorPreview() {
    MetroLimaGOTheme {
        HomeScreenContent(uiState = HomeUiState.Error("No se pudo cargar la información."), onStationClick = {})
    }
}
