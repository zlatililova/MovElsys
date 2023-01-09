package com.example.movelsys.presentation_layer.activity_tracking

import android.app.Activity
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    Column {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            item {
                TopBarFragment(navController)
                viewModel.updateStepCount()
                Text(
                    text = "Today's steps: ", fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(20.dp)
                )
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = viewModel.steps.toString(), fontFamily = FontFamily.Serif,
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
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            .padding(start = 0.dp)
                            .size(width = 200.dp, height = 60.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedButton(
                        onClick = { viewModel.setNewGoalSteps() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        modifier = Modifier
                            .padding(start = 25.dp, end = 0.dp)
                            //.fillMaxWidth()
                            .size(width = 250.dp, height = 60.dp)
                    ) {
                        Text(
                            text = "Change step goal",
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }

            }
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }
}