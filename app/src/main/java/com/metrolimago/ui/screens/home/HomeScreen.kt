package com.metrolimago.ui.screens.home

// Imports de UI necesarios
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Import para viewModel()

// Imports del modelo y tema
import com.metrolimago.R
import com.metrolimago.data.model.Alerta
import com.metrolimago.ui.theme.*

@Composable
fun HomeScreen(
    // --- CONEXIÓN FINAL ---
    // Usamos viewModel() con la Factory para obtener la instancia real
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    onStationClick: (String) -> Unit = {}
) {
    // Obtenemos el estado REAL del ViewModel usando collectAsState
    val uiState by homeViewModel.uiState.collectAsState()

    // Llamamos al Composable que contiene la UI, pasándole el estado real
    HomeScreenContent(
        uiState = uiState,
        onStationClick = onStationClick
    )
}

/**
 * Este Composable contiene la UI real y reacciona al estado (uiState).
 * No necesita cambios respecto a la versión de Flavio.
 */
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onStationClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(32.dp)) }

        // --- LOGO ---
        item {
            Text(text = "Bienvenido a", fontSize = 16.sp, color = Color.Gray)
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
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 26.sp,
                    color = TextPrimary // Usar color del tema
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- SECCIÓN DE ESTADO DEL SISTEMA (REACTIVA) ---
        item {
            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(vertical = 24.dp))
                }
                is HomeUiState.Success -> {
                    val primeraAlerta = uiState.alertas.firstOrNull()
                    EstadoSistemaCard(alerta = primeraAlerta ?: Alerta(0, "Todos los sistemas operativos", "Servicio normal", "INFO"))
                }
                is HomeUiState.Error -> {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f))
                    ) {
                        Text(
                            text = uiState.message,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- TARJETA DE ESTACIONES CERCANAS ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MetroLimaPurple) // Usar color del tema
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estaciones Cercanas",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = { /* TODO */ }) {
                            // Considerar usar un Icon vectorial si tienes uno para "ubicarme"
                            // Icon(Icons.Default.MyLocation, ...)
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ubicarme), // Asegúrate que este drawable existe
                                contentDescription = "Ubicarme",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Ubicarme", color = Color.White)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = BackgroundLight, // Usar color del tema
                                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(DisabledGray), // Usar color del tema
                                contentAlignment = Alignment.Center
                            ) {
                                Text("GRÁFICO DEL MAPA AQUÍ", color = TextSecondary) // Usar color del tema
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            // Los datos aquí siguen siendo falsos, se conectarán después
                            StationItem(number = "1", name = "Grau", distance = "~1.4 km") {
                                onStationClick("Grau")
                            }
                            StationItem(number = "2", name = "Gamarra", distance = "~5.3 km") {
                                onStationClick("Gamarra")
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

// Composable para la tarjeta de estado (ajustado para usar colores del tema)
@Composable
fun EstadoSistemaCard(alerta: Alerta) {
    val (icon, color) = when (alerta.tipo.uppercase()) { // Usar uppercase para seguridad
        "INFO" -> Icons.Default.CheckCircle to MetroLimaGreen
        "WARNING", "ADVERTENCIA" -> Icons.Default.Warning to Color(0xFFFFA000) // Amarillo estándar
        "ERROR", "PELIGRO" -> Icons.Default.Error to Color.Red // Ícono de error
        else -> Icons.Default.Info to TextSecondary // Gris por defecto
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundLight), // Usar color del tema
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary // Usar color del tema
                )
                // Mostrar mensaje solo si no está vacío
                if (alerta.mensaje.isNotBlank()) {
                    Text(
                        text = alerta.mensaje,
                        fontSize = 14.sp,
                        color = TextSecondary // Usar color del tema
                    )
                }
            }
        }
    }
}

// Tu Composable original para StationItem (ajustado para usar colores del tema)
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
                .size(24.dp)
                .clip(CircleShape)
                .background(MetroLimaPurple.copy(alpha = 0.1f)), // Fondo suave
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontWeight = FontWeight.Bold,
                color = MetroLimaPurple // Usar color del tema
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary) // Usar color del tema
            Text(text = "Distancia aprox.: $distance", color = TextSecondary, fontSize = 14.sp) // Usar color del tema
        }

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Ubicación",
            tint = MetroLimaPurple // Usar color del tema
        )
    }
}

// --- PREVIEWS (No necesitan el ViewModel real) ---
@Preview(showBackground = true, name = "HomeScreen Loading")
@Composable
fun HomeScreenLoadingPreview() {
    MetroLimaGOTheme {
        HomeScreenContent(uiState = HomeUiState.Loading, onStationClick = {})
    }
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

