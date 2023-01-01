package com.example.movelsys.presentation_layer

import android.app.Activity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.Screen
import com.example.movelsys.presentation_layer.activity.BottomBarFragment
import com.example.movelsys.presentation_layer.activity.HistoryRowFragment
import com.example.movelsys.presentation_layer.activity.HistoryViewModel
import com.example.movelsys.presentation_layer.activity.TopBarFragment

@Composable
fun HistoryScreenFragment(
    navController: NavController,
    viewModel: HistoryViewModel
) {
    BottomBarFragment(navController = navController)
    /*val context = LocalContext.current
    val activity = context as Activity
    TopBarFragment()
    viewModel.startGoogleFit(context, activity)

    /*while(viewModel.googleFitHistory.isEmpty()){
        LoadingAnimation()
    }*/
    LazyColumn {
        viewModel.googleFitHistory.forEach { it ->
            item {
                HistoryRowFragment(datapoint = it)
            }


        }
    }*/
    Text(text = "History!")

}



