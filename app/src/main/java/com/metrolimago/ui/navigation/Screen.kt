package com.metrolimago.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object StationList : Screen("station_list")
    object RoutePlanner : Screen("route_planner")
    object Settings : Screen("settings")
    object StationDetail : Screen("station_detail/{stationId}") {
        fun createRoute(stationId: String) = "station_detail/$stationId"
    }
}
