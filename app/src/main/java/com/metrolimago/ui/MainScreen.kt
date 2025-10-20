package com.metrolimago.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.metrolimago.R // Importante: Importa tus propios recursos
import com.metrolimago.ui.navigation.AppNavigation
import com.metrolimago.ui.navigation.Screen

// CAMBIO: Ahora 'icon' es un Int que representa el ID del drawable (ej: R.drawable.ic_inicio)
data class BottomNavItem(val label: String, @DrawableRes val icon: Int, val route: String)

/**
 * Esta es la pantalla principal de la aplicación.
 * Contiene la barra de navegación inferior y el contenedor
 * donde se mostrarán todas las demás pantallas.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController = navController) }
    ) {
        AppNavigation(navController = navController)
    }
}

/**
 * Barra de navegación inferior actualizada para usar los íconos personalizados de la carpeta 'drawable'.
 */
@Composable
fun MyBottomBar(navController: NavHostController) {
    // CAMBIO: Usamos los IDs de tus drawables en lugar de los iconos de Material.
    val navItems = listOf(
        BottomNavItem("Inicio", R.drawable.ic_inicio, Screen.Home.route),
        BottomNavItem("Estaciones", R.drawable.ic_estaciones, Screen.StationList.route),
        BottomNavItem("Planificar", R.drawable.ic_planificar, Screen.RoutePlanner.route),
        BottomNavItem("Ajustes", R.drawable.ic_ajustes, Screen.Settings.route)
    )

    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        navItems.forEach { item ->
            NavigationBarItem(
                selected = (currentRoute == item.route),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                label = { Text(text = item.label) },
                // CAMBIO: Usamos painterResource para dibujar el ícono desde su ID.
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.label) }
            )
        }
    }
}
