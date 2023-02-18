package com.example.movelsys.presentation_layer.activity_tracking.history

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.LoadingAnimation
import com.example.movelsys.data_layer.google_fit.fetchSensorData.GoogleSensorDataImplementation
import com.example.movelsys.data_layer.google_fit.fetchHistoryData.GoogleFetchDataImplementation
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment
import com.example.movelsys.ui.theme.MovelsysTheme

@Composable
fun HistoryScreenFragment(
    navController: NavController,
    viewModel: HistoryViewModel
) {
    val context = LocalContext.current
    val activity = context as Activity
    viewModel.getActivityAndContext(context, activity)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBarFragment(navController, false)
        Text(
            text = "History",
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(top = 15.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedButton(
            onClick = { viewModel.startGoogleFit() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .size(width = 100.dp, height = 50.dp)
        ) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (viewModel.timesDataWasFetched <= 0) {
                Column {
                    LoadingAnimation()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        HistoryGrid(viewModel)
                    }
                }
            }
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }
}

@Composable
fun RowScope.HistoryTableCell(
    text: String,
    weight: Float,
    type: String,
    steps: Int,
    viewModel: HistoryViewModel
) {

    val colorPalette =
        mutableListOf(Color.LightGray, MaterialTheme.colors.secondary, MaterialTheme.colors.primary)

    if (type == "icon") {
        Box(
            modifier = Modifier
                .weight(weight)
                .padding(start = 45.dp)
        ) {
            CircularProgressIndicator(
                progress = 1f,
                strokeWidth = 7.dp,
                modifier = Modifier.size(30.dp),
                color = colorPalette[viewModel.calculatePercentageOfGoal(steps).first],
            )
            CircularProgressIndicator(
                progress = viewModel.calculatePercentageOfGoal(steps).second,
                strokeWidth = 7.dp,
                modifier = Modifier.size(30.dp),
                color = colorPalette[viewModel.calculatePercentageOfGoal(steps).first + 1],
            )
        }
    }
    if (type == "heading") {
        Text(
            text = text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .weight(weight)
        )
    }
    if (type == "text") {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .weight(weight)
                .padding(8.dp)
        )
    }
}


@Composable
fun HistoryGrid(viewModel: HistoryViewModel) {
    val dateColumnWeight = .3f
    val stepsColumnWeight = .4f
    val goalColumnWeight = .3f
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            HistoryTableCell(text = "Date", weight = dateColumnWeight, "heading", 0, viewModel)
            HistoryTableCell(text = "Steps", weight = stepsColumnWeight, "heading", 0, viewModel)
            HistoryTableCell(text = "Goal", weight = goalColumnWeight, "heading", 0, viewModel)
        }
        viewModel.googleFitDates.forEachIndexed { index, date ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                HistoryTableCell(
                    text = date,
                    weight = dateColumnWeight,
                    "text",
                    0,
                    viewModel
                )
                HistoryTableCell(
                    text = viewModel.googleFitSteps[index].toString(),
                    weight = stepsColumnWeight,
                    "text",
                    0,
                    viewModel
                )
                HistoryTableCell(
                    text = "Goal met",
                    weight = goalColumnWeight,
                    "icon",
                    viewModel.googleFitSteps[index],
                    viewModel
                )
            }
            Divider(
                color = MaterialTheme.colors.primary,
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}