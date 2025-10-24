package com.metrolimago.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.route_planner.PlanificadorRutaScreen
import com.metrolimago.ui.screens.station_list.ListaEstacionesScreen
import com.metrolimago.ui.screens.station_detail.DetalleEstacionScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToStationList = { navController.navigate(Screen.StationList.route) },
                onNavigateToRoutePlanner = { navController.navigate(Screen.RoutePlanner.route) }
            )
        }

        composable(Screen.StationList.route) {
            ListaEstacionesScreen(
                onStationClick = { stationName ->
                    navController.navigate(Screen.StationDetail.createRoute(stationName))
                }
            )
        }

        composable(
            Screen.StationDetail.route,
            arguments = listOf()
        ) { backStackEntry ->
            val stationName = backStackEntry.arguments?.getString("stationId") ?: "Desconocida"
            DetalleEstacionScreen(
                stationName = stationName,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.RoutePlanner.route) {
            PlanificadorRutaScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Settings.route) {
            Text("PANTALLA DE AJUSTES")
        }
    }
}
