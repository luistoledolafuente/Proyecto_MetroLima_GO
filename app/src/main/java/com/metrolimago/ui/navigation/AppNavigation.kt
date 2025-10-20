package com.metrolimago.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.station_list.ListaEstacionesScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    // Para que veas tu pantalla de inmediato, la he puesto como la de inicio.
    // Puedes cambiarla de vuelta a Screen.Home.route cuando quieras.
    NavHost(navController = navController, startDestination = Screen.StationList.route) {

        // --- Definición de cada pantalla ---

        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        // --- ¡AQUÍ ESTÁ LA CORRECCIÓN! ---
        // Ahora sí se llama a tu Composable 'ListaEstacionesScreen'.
        composable(route = Screen.StationList.route) {
            ListaEstacionesScreen(
                onStationClick = { stationName ->
                    // Lógica para cuando se haga clic en una estación.
                    println("Clic en: $stationName")
                    // Futuro: navController.navigate("station_detail_screen/$stationName")
                }
            )
        }

        composable(route = Screen.RoutePlanner.route) {
            Text("PANTALLA DE PLANIFICADOR DE RUTA")
        }

        composable(route = Screen.Settings.route) {
            Text("PANTALLA DE AJUSTES")
        }

        composable(route = Screen.StationDetail.route) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId")
            Text("PANTALLA DE DETALLE para la estación: $stationId")
        }
    }
}