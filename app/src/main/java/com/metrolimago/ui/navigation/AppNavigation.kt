package com.metrolimago.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType // <-- Import necesario para los argumentos
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument // <-- Import necesario para los argumentos
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.route_planner.PlanificadorRutaScreen
import com.metrolimago.ui.screens.station_list.ListaEstacionesScreen
// AHORA ESTE IMPORT FUNCIONARÁ PORQUE SUBISTE EL ARCHIVO:
import com.metrolimago.ui.screens.station_detail.DetalleEstacionScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // Iniciamos en Home
    ) {

        // --- PANTALLAS PRINCIPALES (Tabs con FADE) ---

        composable(
            route = Screen.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
            popExitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            // Usamos la versión de HomeScreen que permite navegar a las otras pantallas
            HomeScreen(
                onNavigateToStationList = { navController.navigate(Screen.StationList.route) },
                onNavigateToRoutePlanner = { navController.navigate(Screen.RoutePlanner.route) }
                // El 'onStationClick' del conflicto de Git parecía estar en desarrollo,
                // pero este modelo es más funcional.
            )
        }

        composable(
            route = Screen.StationList.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
            popExitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            ListaEstacionesScreen(
                onStationClick = { stationName ->
                    // Usamos la función createRoute de tu archivo Screen.kt
                    navController.navigate(Screen.StationDetail.createRoute(stationName))
                }
            )
        }

        // --- PANTALLAS SECUNDARIAS (con SLIDE) ---

        composable(
            route = Screen.RoutePlanner.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
        ) {
            PlanificadorRutaScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Settings.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
        ) {
            Text("PANTALLA DE AJUSTES") // (Placeholder)
        }

        // --- CÓDIGO CORREGIDO (Error 2) ---
        composable(
            // 1. Usamos la ruta de tu archivo Screen.kt
            route = Screen.StationDetail.route,

            // 2. Definimos el argumento "stationId" que espera la ruta
            arguments = listOf(navArgument("stationId") { type = NavType.StringType }),

            // 3. Añadimos las transiciones
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
        ) { backStackEntry ->

            // 4. Leemos el argumento "stationId"
            val stationId = backStackEntry.arguments?.getString("stationId") ?: "Desconocida"

            // 5. Llamamos a tu Composable DetalleEstacionScreen
            DetalleEstacionScreen(
                stationName = stationId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}