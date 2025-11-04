package com.metrolimago.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.route_planner.PlanificadorRutaScreen
import com.metrolimago.ui.screens.station_list.ListaEstacionesScreen

@Composable
fun AppNavigation(navController: NavHostController) {
<<<<<<< HEAD
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
            HomeScreen(
                onStationClick = { stationId ->
                    navController.navigate("${Screen.StationDetail.route}/$stationId")
=======
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
>>>>>>> main
                }
            )
        }

        composable(
<<<<<<< HEAD
            route = Screen.StationList.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
            popExitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            ListaEstacionesScreen(
                onStationClick = { stationName ->
                    navController.navigate("${Screen.StationDetail.route}/$stationName")
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

        composable(
            route = Screen.StationDetail.route,
            // arguments = ... (Asegúrate que Screen.kt tenga la ruta "station_detail_screen/{stationId}")
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
        ) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId")
            Text("PANTALLA DE DETALLE para la estación: $stationId") // (Reemplaza con tu DetalleEstacionScreen)
        }
    }
}
=======
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
>>>>>>> main
