package com.example.movelsys.presentation_layer.activity

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RankingScreenFragment(navController: NavController){
    BottomBarFragment(navController = navController)
    Text(text = "Ranking!")

}