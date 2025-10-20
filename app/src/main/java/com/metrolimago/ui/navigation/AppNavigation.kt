package com.metrolimago.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metrolimago.ui.screens.home.HomeScreen

/**
 * Este es el "mapa" principal de nuestra app.
 * El NavHost es como un contenedor que muestra la pantalla correcta
 * según la ruta actual.
 */
@Composable
fun AppNavigation(navController: NavHostController) {
    // 'startDestination' es la primera pantalla que se mostrará al abrir la app.
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        // --- Aquí definimos cada pantalla de nuestro mapa ---

        // Cuando la ruta sea "home", mostramos la pantalla de inicio.
        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        // Cuando la ruta sea "station_list", mostramos la lista de estaciones.
        composable(route = Screen.StationList.route) {
            // TODO: Aquí irá el ListaEstacionesScreen() de un compañero.
            Text("PANTALLA DE LISTA DE ESTACIONES (ListaEstacionesScreen)")
        }

        // Cuando la ruta sea "route_planner", mostramos el planificador.
        composable(route = Screen.RoutePlanner.route) {
            Text("PANTALLA DE PLANIFICADOR DE RUTA (PlanificadorRutaScreen)")
        }

        // Cuando la ruta sea "settings", mostramos la configuración.
        composable(route = Screen.Settings.route){
            Text("PANTALLA DE CONFIGURACIÓN (ConfiguracionScreen)")
        }

        // Esta es la pantalla que necesita un "argumento" (el ID de la estación).
        composable(route = Screen.StationDetail.route) { backStackEntry ->
            // Recogemos el ID de la estación desde la ruta.
            val stationId = backStackEntry.arguments?.getString("stationId")

            // TODO: Aquí irá el DetalleEstacionScreen() de un compañero.
            Text("PANTALLA DE DETALLE para la estación: $stationId")
        }
    }
}