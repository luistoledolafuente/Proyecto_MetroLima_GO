package com.metrolimago.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.metrolimago.R
import com.metrolimago.ui.navigation.AppNavigation
import com.metrolimago.ui.navigation.Screen

// No se necesitan cambios en BottomNavItem
data class BottomNavItem(val label: String, val icon: Int, val route: String)

/**
 * Pantalla principal corregida.
 * APLICA el padding del Scaffold para evitar la pantalla en blanco.
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController = navController) }
    ) { innerPadding ->
        // ESTA ES LA CORRECCIÓN CLAVE PARA QUE LAS PANTALLAS NO SALGAN EN BLANCO
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavigation(navController = navController)
        }
    }
}

/**
 * Barra de navegación inferior corregida.
 * Usa NavigationBar y NavigationBarItem de Material 3.
 */
@Composable
fun MyBottomBar(navController: NavHostController) {
    val navItems = listOf(
        BottomNavItem("Inicio", R.drawable.ic_inicio, Screen.Home.route),
        BottomNavItem("Estaciones", R.drawable.ic_estaciones, Screen.StationList.route),
        BottomNavItem("Planificar", R.drawable.ic_planificar, Screen.RoutePlanner.route),
        BottomNavItem("Ajustes", R.drawable.ic_ajustes, Screen.Settings.route)
    )

    // USA "NavigationBar", NO "BottomNavigation"
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}