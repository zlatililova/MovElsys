package com.example.movelsys

sealed class Screen(val route: String){
    object Welcome: Screen(route = "welcome_screen")
    object Register: Screen(route = "register_screen")
    object Login: Screen(route = "login_screen")

}
