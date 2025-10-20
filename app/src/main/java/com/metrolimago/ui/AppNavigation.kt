package com.metrolimago.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.metrolimago.ui.screens.home.HomeScreen
import com.metrolimago.ui.screens.detalle.DetalleEstacionScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStationClick = { navController.navigate("detalle") }
            )
        }
        composable("detalle") {
            DetalleEstacionScreen()
        }
    }
}

