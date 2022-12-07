package com.example.movelsys

sealed class Screen(val route: String){
    object Welcome: Screen(route = "welcome_screen")
    object Register: Screen(route = "register_screen")
    object Login: Screen(route = "login_screen")
    object Main: Screen(route = "main_screen")
    object Load: Screen(route = "load_screen")

}
