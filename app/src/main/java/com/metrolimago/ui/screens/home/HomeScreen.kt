package com.metrolimago.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolimago.R
import com.metrolimago.ui.theme.MetroLimaGOTheme

/**
 * Pantalla principal del proyecto MetroLima GO.
 * Muestra el logo, estado del sistema y estaciones cercanas.
 */
@Composable
fun HomeScreen(onStationClick: () -> Unit = {}) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espaciador superior
        item { Spacer(modifier = Modifier.height(32.dp)) }

        // --- 1. SECCIÓN DEL LOGO ---
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

        // Espaciador
        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- 2. CHIP DE ESTADO DEL SISTEMA ---
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE6FCEC))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Check",
                        tint = Color(0xFF008A05)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Todos los sistemas operativos",
                        color = Color(0xFF008A05),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Espaciador
        item { Spacer(modifier = Modifier.height(24.dp)) }

        // --- 3. TARJETA DE ESTACIONES CERCANAS ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column {
                    // Fila superior con título y botón "Ubicarme"
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

                    // Contenedor para el mapa y la lista
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Placeholder para el gráfico del mapa
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

                            // Lista de estaciones (datos de ejemplo)
                            StationItem(number = "1", name = "Grau", distance = "~1.4 km", onClick = onStationClick)
                            StationItem(number = "2", name = "Gamarra", distance = "~5.3 km", onClick = onStationClick)
                        }
                    }
                }
            }
        }

        // Espaciador inferior
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

/**
 * Componente que representa una estación en la lista.
 */
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

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun HomeScreenNewPreview() {
    MetroLimaGOTheme {
        HomeScreen()
    }
}
