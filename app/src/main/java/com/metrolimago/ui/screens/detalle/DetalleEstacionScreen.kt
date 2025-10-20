package com.metrolimago.ui.screens.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolimago.ui.theme.MetroLimaGOTheme

@Composable
fun DetalleEstacionScreen(
    nombre: String = "Estación San Juan",
    linea: String = "Línea 1 - Roja",
    servicios: List<String> = listOf("WiFi gratuito", "Baños públicos", "Seguridad 24h")
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalles de Estación",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF6A3DE8),
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = nombre,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = linea,
                    fontSize = 18.sp,
                    color = Color(0xFF4CAF50)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Servicios disponibles:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                servicios.forEach {
                    Text(text = "• $it", fontSize = 16.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleEstacionPreview() {
    MetroLimaGOTheme {
        DetalleEstacionScreen()
    }
}
