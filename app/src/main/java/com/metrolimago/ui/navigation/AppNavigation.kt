package com.metrolimago.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.route_planner.PlanificadorRutaScreen // Asegúrate que esta pantalla exista
import com.metrolimago.ui.screens.station_list.ListaEstacionesScreen
import com.metrolimago.ui.screens.station_detail.DetalleEstacionScreen

/**
 * Define el grafo de navegación principal de la aplicación.
 * Gestiona qué pantalla se muestra según la ruta actual.
 *
 * @param navController El controlador de navegación gestionado por MainScreen.
 */
@Composable
fun AppNavigation(navController: NavHostController) {

    // El NavHost es el contenedor que intercambia las pantallas (Composables).
    // startDestination define la pantalla inicial.
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        // --- Definición de cada destino de navegación ---

        composable(route = Screen.Home.route) {
            HomeScreen(
                // onStationClick = { stationName -> navController.navigate(Screen.StationDetail.createRoute(stationName)) }
            )
        }

        composable(route = Screen.StationList.route) {
            ListaEstacionesScreen(
                onStationClick = { stationName ->
                    // Navega a la pantalla de detalle usando la ruta segura de Screen
                    navController.navigate(Screen.StationDetail.createRoute(stationName))
                }
            )
        }

        composable(
            route = Screen.StationDetail.route, // Ruta base: "station_detail/{stationId}"
            arguments = listOf(navArgument("stationId") { type = NavType.StringType }) // Define el argumento
        ) { backStackEntry ->
            // Extrae el argumento "stationId" de la ruta
            val stationName = backStackEntry.arguments?.getString("stationId")

            DetalleEstacionScreen(
                stationName = stationName ?: "Estación Desconocida", // Valor por defecto si falla
                onBackClick = {
                    navController.popBackStack() // Acción para el botón "atrás"
                }
            )
        }

        composable(route = Screen.RoutePlanner.route) {
            PlanificadorRutaScreen(
                onBackClick = { navController.popBackStack() } // Acción para el botón "atrás"
            )
        }

        composable(route = Screen.Settings.route) {
            // TODO: Crear e integrar la pantalla ConfiguracionScreen aquí
            Text("PANTALLA DE AJUSTES (SettingsScreen)")
        }
    }
}

