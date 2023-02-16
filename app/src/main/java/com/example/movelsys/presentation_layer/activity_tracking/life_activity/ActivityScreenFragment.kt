package com.example.movelsys.presentation_layer.activity_tracking.life_activity

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment

@Composable
fun ActivityScreenFragment(navController: NavController, viewModel: ActivityViewModel) {
    viewModel.subscribeToStepsListener(
        context = LocalContext.current,
        activity = LocalContext.current as Activity
    )
    viewModel.activity = LocalContext.current as Activity
    viewModel.fetchLastSavedSteps()
    Column {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            item {
                TopBarFragment(navController, false)
                viewModel.updateStepCount()
                Text(
                    text = "Daily steps: ",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                )
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = viewModel.steps.toString(),
                        fontSize = 100.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
                    CircularProgressIndicator(
                        progress = viewModel.calculatePercentageOfGoal(
                            viewModel.steps,
                            viewModel.goalSteps
                        ),
                        strokeWidth = 20.dp,
                        modifier = Modifier.size(350.dp),
                        color = MaterialTheme.colors.secondary
                    )
                }
                LazyRow(content = {
                    item {
                        ProgressBar(
                            period = "Weekly",
                            percentage = viewModel.calculatePercentageOfGoal(
                                viewModel.weeklySteps,
                                (viewModel.goalSteps * 7)
                            ),
                            steps = viewModel.weeklySteps
                        )
                        ProgressBar(
                            period = "Monthly",
                            percentage = viewModel.calculatePercentageOfGoal(
                                viewModel.monthlySteps,
                                (viewModel.goalSteps * 30)
                            ),
                            steps = viewModel.monthlySteps
                        )
                    }
                }
                )
            }
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }
}

@Composable
fun ProgressBar(period: String, percentage: Float, steps: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$period steps: ",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(20.dp)
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = steps.toString(),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
            CircularProgressIndicator(
                progress = percentage,
                strokeWidth = 15.dp,
                modifier = Modifier.size(150.dp),
                color = MaterialTheme.colors.secondary
            )
        }
    }
}