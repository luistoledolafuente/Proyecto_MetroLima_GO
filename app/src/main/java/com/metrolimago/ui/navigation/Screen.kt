package com.metrolimago.ui.navigation

/**
 * Esta es nuestra lista de "direcciones" o "rutas" para cada pantalla de la app.
 * Usamos una 'sealed class' para que solo podamos usar las pantallas que definimos aquí.
 * ¡Es como un contrato para evitar errores de tipeo!
 */
sealed class Screen(val route: String) {
    // La dirección para nuestra pantalla de Inicio
    object Home : Screen("home")

    // La dirección para la pantalla con la lista de estaciones
    object StationList : Screen("station_list")

    // La dirección para el planificador de rutas
    object RoutePlanner : Screen("route_planner")

    // La dirección para la pantalla de configuración
    object Settings : Screen("settings")

    // La dirección para el detalle de una estación.
    // La parte "{stationId}" es un "espacio" que llenaremos con el nombre de la estación.
    object StationDetail : Screen("station_detail/{stationId}") {
        // Esta función simple nos ayuda a crear la ruta completa.
        // Por ejemplo: createRoute("Gamarra") nos dará "station_detail/Gamarra"
        fun createRoute(stationId: String) = "station_detail/$stationId"
    }
}

