package com.example.movelsys

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movelsys.data_layer.authentication.AuthDataImplementation
import com.example.movelsys.data_layer.google_fit.fetchSensorData.GoogleSensorDataImplementation
import com.example.movelsys.data_layer.google_fit.fetchHistoryData.GoogleFetchDataImplementation
import com.example.movelsys.data_layer.profileManagement.ProfileUpdateImplementation
import com.example.movelsys.domain_layer.use_cases.*
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryScreenFragment
import com.example.movelsys.presentation_layer.activity_tracking.ActivityScreenFragment
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryViewModel
import com.example.movelsys.presentation_layer.activity_tracking.RankingScreenFragment
import com.example.movelsys.presentation_layer.activity_tracking.life_activity.ActivityViewModel
import com.example.movelsys.presentation_layer.authentication.LoginScreenFragment
import com.example.movelsys.presentation_layer.authentication.LoginViewModel
import com.example.movelsys.presentation_layer.authentication.RegisterScreenFragment
import com.example.movelsys.presentation_layer.authentication.RegisterViewModel
import com.example.movelsys.presentation_layer.profile.ProfileScreenFragment
import com.example.movelsys.presentation_layer.profile.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route //checkIfUserIsLoggedIn()
    ) {
        composable(
            route = Screen.Welcome.route
        ) {
            WelcomeScreenFragment(navController = navController)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterScreenFragment(
                navController = navController,
                viewModel = RegisterViewModel(
                    registerUseCase = RegisterUseCase(AuthDataImplementation()),
                    validateCredentials = ValidateCredentials()
                )
            )
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginScreenFragment(
                navController = navController,
                viewModel = LoginViewModel(
                    loginUseCase = LoginUseCase(AuthDataImplementation()),
                    validateCredentials = ValidateCredentials()
                )
            )
        }
        composable(
            route = Screen.History.route
        ) {
            HistoryScreenFragment(
                navController,
                viewModel = HistoryViewModel(
                    googleFetchUseCase = GoogleFetchUseCase(
                        googleFetchData = GoogleFetchDataImplementation(),
                        googleSensorData = GoogleSensorDataImplementation()
                    )
                )
            )
        }
        composable(
            route = Screen.Load.route
        ) {
            LoadingAnimation()
        }
        composable(
            route = Screen.Activity.route
        ) {
            ActivityScreenFragment(
                navController,
                viewModel = ActivityViewModel(
                    googleFetchUseCase = GoogleFetchUseCase(
                        googleFetchData = GoogleFetchDataImplementation(),
                        googleSensorData = GoogleSensorDataImplementation()
                    )
                )
            )
        }
        composable(
            route = Screen.Ranking.route
        ) {
            RankingScreenFragment(navController)
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreenFragment(navController, viewModel = ProfileViewModel(profileUpdateUseCase = ProfileUpdateUseCase(profileUpdate = ProfileUpdateImplementation()), validateCredentials = ValidateCredentials()))
        }
    }
}

fun checkIfUserIsLoggedIn(): String{
    val user = Firebase.auth.currentUser
    if(user != null){
        return Screen.Activity.route
    }
    return Screen.Welcome.route
}