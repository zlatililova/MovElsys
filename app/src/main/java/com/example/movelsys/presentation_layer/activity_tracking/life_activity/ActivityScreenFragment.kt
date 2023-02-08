package com.example.movelsys.presentation_layer.activity_tracking

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movelsys.presentation_layer.activity_tracking.life_activity.ActivityViewModel

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
                /*Text(
                    text = "Today's steps: ",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp)
                )
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = viewModel.steps.toString(),
                        fontSize = 100.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
                    CircularProgressIndicator(
                        progress = viewModel.calculatePersentageOfGoal(),
                        strokeWidth = 20.dp,
                        modifier = Modifier.size(350.dp),
                        color = MaterialTheme.colors.secondary
                    )
                }*/
                ProgressBar(period = "Daily", persentage = viewModel.calculatePersentageOfGoal(viewModel.steps, viewModel.goalSteps), steps = viewModel.steps)
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                ProgressBar(period = "Weekly", persentage = viewModel.calculatePersentageOfGoal(viewModel.weeklySteps, (viewModel.goalSteps*7)), steps = viewModel.weeklySteps)
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                ProgressBar(period = "Monthly", persentage = viewModel.calculatePersentageOfGoal(viewModel.monthlySteps, (viewModel.goalSteps*30)), steps = viewModel.monthlySteps)
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    OutlinedTextField(
                        value = viewModel.newGoal,
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                viewModel.newGoal = it
                            }
                        },
                        label = { Text("Current goal: ${viewModel.goalSteps}") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.secondary,
                            unfocusedBorderColor = MaterialTheme.colors.primary,
                        ),
                        modifier = Modifier
                            .padding(start = 0.dp, end = 20.dp, bottom = 30.dp)
                            .size(width = 200.dp, height = 60.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedButton(
                        onClick = { viewModel.setNewGoalSteps() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        modifier = Modifier
                            .padding(start = 0.dp, end = 25.dp)
                            //.fillMaxWidth()
                            .size(width = 150.dp, height = 60.dp)
                    ) {
                        Text(
                            text = "Change step goal",
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
            }
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }
}

@Composable
fun ProgressBar(period: String, persentage: Float, steps: Int){
    Text(
        text = "$period steps: ",
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(20.dp)
    )
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = steps.toString(),
            fontSize = 100.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )
        CircularProgressIndicator(
            progress = persentage,
            strokeWidth = 25.dp,
            modifier = Modifier.size(350.dp),
            color = MaterialTheme.colors.secondary
        )
    }
}