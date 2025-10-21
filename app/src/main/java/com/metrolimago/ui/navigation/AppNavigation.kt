package com.metrolimago.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.station_list.ListaEstacionesScreen
import com.metrolimago.ui.screens.station_detail.DetalleEstacionScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    // Mantenemos tu startDestination. Si quieres que empiece en Home, cámbialo a "home".
    NavHost(navController = navController, startDestination = "station_list") {

        composable(route = "home") {
            HomeScreen(
                // Si la HomeScreen también necesita navegar al detalle, se haría igual que la lista.
                // onStationClick = { stationName ->
                //     navController.navigate("station_detail_screen/$stationName")
                // }
            )
        }

        composable(route = "station_list") {
            ListaEstacionesScreen(
                onStationClick = { stationName ->
                    // Tu lógica de navegación funciona perfecto. No se cambia.
                    navController.navigate("station_detail_screen/$stationName")
                }
            )
        }

        composable(route = "station_detail_screen/{stationName}") { backStackEntry ->
            val stationName = backStackEntry.arguments?.getString("stationName")

            // --- ¡AQUÍ ESTÁ LA MEJORA! ---
            // Ahora le pasamos la función para que el botón "atrás" funcione.
            DetalleEstacionScreen(
                stationName = stationName ?: "Sin nombre",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // También he añadido las pantallas temporales para las otras rutas
        // para que la barra de navegación no falle si haces clic en ellas.
        composable(route = "route_planner") {
            Text("PANTALLA DE PLANIFICADOR DE RUTA")
        }

        composable(route = "settings") {
            Text("PANTALLA DE AJUSTES")
        }
    }
}

