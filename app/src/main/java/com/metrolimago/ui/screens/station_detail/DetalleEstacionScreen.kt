package com.metrolimago.ui.screens.station_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolimago.R // Asegúrate de que R se importe para los íconos/imágenes
import com.metrolimago.ui.theme.*

/**
 * Pantalla de detalle de estación, rediseñada para ser idéntica al prototipo final.
 *
 * @param stationName El nombre de la estación a mostrar.
 * @param onBackClick Función para manejar el clic en el botón "Atrás".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEstacionScreen(
    stationName: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Estaciones",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
                actions = {
                    // Espacio en blanco para centrar el título correctamente
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // 1. Tarjeta principal con la información e imagen
            item {
                MainStationInfoCard(
                    stationName = stationName,
                    linea = "Línea 1",
                    distrito = "San Juan de Lurigancho"
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // 2. Botones de acción
            item { ActionButtonsRow() }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // 3. Secciones de Servicios y Horarios
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardBackground)
                    .padding(16.dp)
                ) {
                    ServicesSection(servicios = listOf("WiFi Gratuito", "Baños Públicos", "Seguridad 24h", "Accesibilidad"))
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    HorariosSection()
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// --- COMPONENTES REUTILIZABLES PARA EL NUEVO DISEÑO ---

@Composable
private fun MainStationInfoCard(stationName: String, linea: String, distrito: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column {
            // Placeholder para la imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(DisabledGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Imagen de la Estación", color = TextSecondary)
            }

            // Contenido de texto
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stationName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoRow(
                    icon = { Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(MetroLimaGreen)) },
                    text = linea
                )
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = TextSecondary) },
                    text = "Distrito: $distrito"
                )
            }
        }
    }
}

@Composable
private fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* TODO: Lógica para planificar ruta */ },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MetroLimaPurple),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Route, contentDescription = "Planificar")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Planificar Ruta")
        }
        OutlinedButton(
            onClick = { /* TODO: Lógica para guardar */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(MetroLimaPurple))
        ) {
            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Guardar", tint = MetroLimaPurple)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Guardar", color = MetroLimaPurple)
        }
    }
}

@Composable
private fun ServicesSection(servicios: List<String>) {
    Column {
        Text(
            text = "Servicios",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        servicios.chunked(2).forEach { rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEach { servicio ->
                    Box(modifier = Modifier.weight(1f)) {
                        InfoRow(
                            icon = { Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MetroLimaGreen) },
                            text = servicio
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun HorariosSection() {
    Column {
        Text(
            text = "Horarios",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(icon = { Icon(Icons.Default.Schedule, null, tint = TextSecondary) }, text = "L-V: 05:00 - 22:00")
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(icon = { Icon(Icons.Default.Schedule, null, tint = TextSecondary) }, text = "Sáb: 06:00 - 22:00")
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(icon = { Icon(Icons.Default.Schedule, null, tint = TextSecondary) }, text = "Dom/Fer: 06:00 - 21:00")
    }
}

@Composable
private fun InfoRow(icon: @Composable () -> Unit, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        icon()
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = TextSecondary, fontSize = 16.sp)
    }
}


// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun DetalleEstacionScreenNewPreview() {
    MetroLimaGOTheme {
        DetalleEstacionScreen(
            stationName = "Estación Gamarra",
            onBackClick = {}
        )
    }
}

