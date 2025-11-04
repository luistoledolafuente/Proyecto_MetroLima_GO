package com.metrolimago.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Esta es la clase que AppNavigation no puede encontrar
sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screen(
        route = "home",
        title = "Inicio",
        icon = Icons.Default.Home
    )

    object StationList : Screen(
        route = "station_list",
        title = "Estaciones",
        icon = Icons.Default.List
    )

    object RoutePlanner : Screen(
        route = "route_planner",
        title = "Planificar",
        icon = Icons.Default.Route
    )

    object Settings : Screen(
        route = "settings",
        title = "Ajustes",
        icon = Icons.Default.Settings
    )

    // Esta es la ruta para el detalle que sí tienes en AppNavigation
    object StationDetail : Screen(
        route = "station_detail/{stationId}",
        title = "Detalle",
        icon = Icons.Default.List // Icono de placeholder
    ) {
        fun createRoute(stationId: String) = "station_detail/$stationId"
    }
}