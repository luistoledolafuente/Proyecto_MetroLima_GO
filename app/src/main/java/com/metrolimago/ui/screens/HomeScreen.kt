package com.metrolimago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.* // Importa todos los componentes de Material 3
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolimago.ui.theme.MetroLimaGOTheme

// --- COMPONENTE DE LA PANTALLA PRINCIPAL ---
@Composable
fun HomeScreen() {
    // Scaffold es ideal para estructuras con Barra Superior (opcional) y Barra Inferior (BottomBar)
    Scaffold(
        // Removimos el TopAppBar para cumplir el requisito de "Mostrar el logo en la parte superior"
        // sin usar una App Bar oficial, dándole más espacio al contenido.

        // 1. Barra de Navegación Inferior (BottomNavigationBar) [cite: 76]
        bottomBar = { AppBottomBar() }

    ) { paddingValues ->
        // Usamos Column para apilar todos los elementos verticalmente: Logo, Mapa, Textos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplica el padding para no tapar la barra inferior
                .padding(horizontal = 16.dp) // Padding a los lados
        ) {
            // 2. Logo "MetroLima GO" en la parte superior [cite: 71]
            Text(
                text = "MetroLima GO (Logo)",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // 3. Box gris como marcador del mapa (placeholder) [cite: 72]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray) // Caja gris
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "MARCADOR DEL MAPA", color = Color.DarkGray)
            }

            // 4. Textos informativos [cite: 74, 75]
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Estaciones Cercanas", // Texto informativo [cite: 74]
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Estado del Servicio: Operando con normalidad.", // Texto informativo [cite: 75]
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// --- COMPONENTE DE LA BARRA INFERIOR ---
@Composable
fun AppBottomBar() {
    BottomAppBar(
        // Usamos el color de fondo primario definido por la Persona 2
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        // Íconos: Inicio, Estaciones, Configuración [cite: 77, 78, 79]
        NavigationBarItem(
            selected = true,
            onClick = { /* De momento no hace nada [cite: 79]*/ },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") }, // [cite: 77]
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* De momento no hace nada [cite: 79]*/ },
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Estaciones") }, // [cite: 78]
            label = { Text("Estaciones") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* De momento no hace nada [cite: 79]*/ },
            icon = { Icon(Icons.Filled.Build, contentDescription = "Configuración") }, // [cite: 79]
            label = { Text("Configuración") }
        )
    }
}


// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MetroLimaGOTheme {
        HomeScreen()
    }
}