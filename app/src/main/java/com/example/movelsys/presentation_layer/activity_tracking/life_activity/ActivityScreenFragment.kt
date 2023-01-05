package com.example.movelsys.presentation_layer.activity_tracking

import android.app.Activity
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.movelsys.presentation_layer.activity_tracking.life_activity.ActivityViewModel

@Composable
fun ActivityScreenFragment(navController: NavController, viewModel: ActivityViewModel) {

    viewModel.subscribeToStepsListener(
        context = LocalContext.current,
        activity = LocalContext.current as Activity
    )
    viewModel.listDataSources(
        context = LocalContext.current,
        activity = LocalContext.current as Activity
    )
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TopBarFragment()
            viewModel.updateStepCount()
            Box{
                Text(text = viewModel.steps.toString())
                CircularProgressIndicator(progress = viewModel.calculatePersentageOfGoal())
            }

        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }

}


