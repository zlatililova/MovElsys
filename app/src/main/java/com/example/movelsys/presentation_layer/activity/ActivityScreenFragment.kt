package com.example.movelsys.presentation_layer.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ActivityScreenFragment(navController: NavController){
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TopBarFragment()
            Text(text = "Activity!")
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }

}