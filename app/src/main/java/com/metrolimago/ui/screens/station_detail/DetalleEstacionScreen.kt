package com.metrolimago.ui.screens.station_detail

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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolimago.data.model.EstacionEntity
import com.metrolimago.ui.theme.MetroLimaGOTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEstacionScreen(
    stationName: String,
    onBackClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Estaciones",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = colorScheme.onSurface
                        )
                    }
                },
                actions = { Spacer(modifier = Modifier.width(48.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.surface)
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

            item {
                MainStationInfoCard(
                    stationName = stationName,
                    linea = "Línea 1",
                    distrito = "San Juan de Lurigancho"
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { ActionButtonsRow() }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    ServicesSection(
                        servicios = listOf("WiFi gratuito", "Baños públicos", "Seguridad 24h", "Accesibilidad")
                    )
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    HorariosSection()
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// --- COMPONENTES ---

@Composable
private fun MainStationInfoCard(stationName: String, linea: String, distrito: String) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceVariant)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(colorScheme.outlineVariant),
                contentAlignment = Alignment.Center
            ) {
                Text("Imagen de la estación", color = colorScheme.onSurfaceVariant)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stationName,
                    style = typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoRow(
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(colorScheme.primary)
                        )
                    },
                    text = linea
                )
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = colorScheme.onSurfaceVariant
                        )
                    },
                    text = "Distrito: $distrito"
                )
            }
        }
    }
}

@Composable
private fun ActionButtonsRow() {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* TODO: Lógica de ruta */ },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Route, contentDescription = "Planificar", tint = colorScheme.onPrimary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Planificar Ruta", color = colorScheme.onPrimary)
        }
        OutlinedButton(
            onClick = { /* TODO: Guardar */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(colorScheme.primary))
        ) {
            Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Guardar", tint = colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Guardar", color = colorScheme.primary)
        }
    }
}

@Composable
private fun ServicesSection(servicios: List<String>) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column {
        Text(
            text = "Servicios",
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        servicios.chunked(2).forEach { rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEach { servicio ->
                    Box(modifier = Modifier.weight(1f)) {
                        InfoRow(
                            icon = {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = colorScheme.primary
                                )
                            },
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
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column {
        Text(
            text = "Horarios",
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow({ Icon(Icons.Default.Schedule, null, tint = colorScheme.onSurfaceVariant) }, "L–V: 05:00 - 22:00")
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow({ Icon(Icons.Default.Schedule, null, tint = colorScheme.onSurfaceVariant) }, "Sáb: 06:00 - 22:00")
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow({ Icon(Icons.Default.Schedule, null, tint = colorScheme.onSurfaceVariant) }, "Dom/Fer: 06:00 - 21:00")
    }
}

@Composable
private fun InfoRow(icon: @Composable () -> Unit, text: String) {
    val colorScheme = MaterialTheme.colorScheme
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        icon()
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = colorScheme.onSurfaceVariant, fontSize = 16.sp)
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun DetalleEstacionScreenPreview() {
    MetroLimaGOTheme {
        DetalleEstacionScreen(stationName = "Estación Gamarra", onBackClick = {})
    }
}
