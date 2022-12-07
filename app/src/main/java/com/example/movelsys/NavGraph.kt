package com.example.movelsys

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movelsys.data_layer.authentication.AuthDataImplementation
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.MainScreenFragment
import com.example.movelsys.presentation_layer.authentication.LoginScreenFragment
import com.example.movelsys.presentation_layer.authentication.LoginViewModel
import com.example.movelsys.presentation_layer.authentication.RegisterScreenFragment
import com.example.movelsys.presentation_layer.authentication.RegisterViewModel

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
        composable(
            route = Screen.Login.route
        ){
            LoginScreenFragment(navController = navController, viewModel = LoginViewModel(validationEmail = ValidateEmail(), validationPassword = ValidatePassword(), loginUseCase = LoginUseCase(AuthDataImplementation())))
        }
        composable(
            route = Screen.Main.route
        ){
            MainScreenFragment()
        }

    }
}

