package com.example.movelsys

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movelsys.data_layer.authentication.AuthDataImplementation
import com.example.movelsys.domain_layer.use_cases.*

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(
            route = Screen.Welcome.route
        ){
            WelcomeScreenFragment(navController = navController)
        }
        composable(
            route = Screen.Register.route
        ){
            RegisterScreenFragment(navController = navController, viewModel = RegisterViewModel(validationName = ValidateName(), validationEmail = ValidateEmail(), validationPassword = ValidatePassword(), validationConfirmPassword = ValidateConfPass(), registerUseCase = RegisterUseCase(AuthDataImplementation())))
        }

    }
}

