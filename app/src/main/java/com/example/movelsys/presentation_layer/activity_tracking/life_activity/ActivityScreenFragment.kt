package com.example.movelsys.presentation_layer.activity_tracking

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.movelsys.presentation_layer.activity_tracking.life_activity.ActivityViewModel

@Composable
fun ActivityScreenFragment(navController: NavController, viewModel: ActivityViewModel){

    viewModel.subscribeToStepsListener(context = LocalContext.current, activity = LocalContext.current as Activity)
    viewModel.listDataSources(context = LocalContext.current, activity = LocalContext.current as Activity)
    viewModel.UpdateStepCount()
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