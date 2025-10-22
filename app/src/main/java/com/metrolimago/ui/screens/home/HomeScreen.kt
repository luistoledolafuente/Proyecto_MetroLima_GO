package com.metrolimago.ui.screens.home

// Tus imports de UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolimago.R
import com.metrolimago.ui.theme.MetroLimaGOTheme


import com.metrolimago.data.model.Alerta
import com.metrolimago.ui.screens.home.HomeUiState

@Composable
fun HomeScreen(
    onStationClick: (String) -> Unit = {},
    // La conexión final con la Factory la hará Luis
    // homeViewModel: HomeViewModel = viewModel(...) // <-- Se añadirá después
) {
    // --- CONEXIÓN TEMPORAL (SOLO PARA QUE COMPILE Y VEAS EL DISEÑO) ---
    // Cambia este valor para probar los diferentes estados en el Preview
    val uiState: HomeUiState = HomeUiState.Loading // Prueba con .Success(...) o .Error(...)

    // Se ha movido el contenido a "HomeScreenContent" para que los Previews funcionen
    HomeScreenContent(
        uiState = uiState,
        onStationClick = onStationClick
    )
}

/**
 * Este Composable contiene la UI real y reacciona al estado (uiState).
 * Así los Previews pueden probar diferentes estados fácilmente.
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

        // --- LOGO (Tu código original) ---
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
                    lineHeight = 26.sp
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- SECCIÓN DE ESTADO DEL SISTEMA (AHORA REACTIVA) ---
        item {
            when (uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(vertical = 24.dp))
                }
                is HomeUiState.Success -> {
                    val primeraAlerta = uiState.alertas.firstOrNull()
                    if (primeraAlerta != null) {
                        EstadoSistemaCard(alerta = primeraAlerta)
                    } else {
                        // Estado por defecto si no hay alertas
                        EstadoSistemaCard(alerta = Alerta(0, "Todos los sistemas operativos", "Servicio normal", "INFO"))
                    }
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

        // --- TARJETA DE ESTACIONES CERCANAS (Tu código original) ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
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
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ubicarme),
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
                                color = Color.White,
                                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("GRÁFICO DEL MAPA AQUÍ")
                            }
                            Spacer(modifier = Modifier.height(16.dp))

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

// Composable para la tarjeta de estado
@Composable
fun EstadoSistemaCard(alerta: Alerta) {
    val (icon, color) = when (alerta.tipo) {
        "INFO" -> Icons.Default.CheckCircle to Color(0xFF2EBD85) // Verde
        "WARNING", "ADVERTENCIA" -> Icons.Default.Warning to Color(0xFFF7C325) // Amarillo
        "ERROR", "PELIGRO" -> Icons.Default.Info to Color.Red // Rojo
        else -> Icons.Default.Info to Color.Gray
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                    color = Color.Black
                )
                Text(
                    text = alerta.mensaje,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// Tu Composable original para StationItem
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
                .background(Color(0xFFEDE7F6)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "Distancia aprox.: $distance", color = Color.Gray, fontSize = 14.sp)
        }

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Ubicación",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

// --- PREVIEWS PARA PROBAR LOS DIFERENTES ESTADOS ---
@Preview(showBackground = true, name = "HomeScreen Loading")
@Composable
fun HomeScreenLoadingPreview() {
    MetroLimaGOTheme {
        HomeScreenContent(uiState = HomeUiState.Loading, onStationClick = {})
    }
}

@Preview(showBackground = true, name = "HomeScreen Success")
@Composable
fun HomeScreenSuccessPreview() {
    MetroLimaGOTheme {
        val alerta = Alerta(1, "Servicio normal", "Operando con normalidad", "INFO")
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