package com.example.movelsys

sealed class Screen(val route: String){
    object Welcome: Screen(route = "welcome_screen")
    object Register: Screen(route = "register_screen")
    object Login: Screen(route = "login_screen")
    object Activity: Screen(route = "activity_screen")
    object Load: Screen(route = "load_screen")
    object History: Screen(route = "history_screen")
    object Ranking: Screen(route = "ranking_screen")
    object TeamDetails: Screen(route = "team_details_screen")
    object Profile: Screen(route = "profile_screen")
}
