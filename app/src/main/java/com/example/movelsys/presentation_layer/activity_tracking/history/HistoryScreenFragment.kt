package com.example.movelsys.presentation_layer

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.data_layer.google_fit.fetchHistoryData.GoogleFetchDataImplementation
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryViewModel
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
        TopBarFragment()
        Text(
            text = "History",
            fontFamily = FontFamily.Serif,
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
            Text(text = "Refresh", textAlign = TextAlign.Center, color = Color.White)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            if (viewModel.timesDataWasFetched > 0) {
                HistoryGrid(dates = viewModel.googleFitDates, steps = viewModel.googleFitSteps)
            }
        }

        Row {
            BottomBarFragment(navController = navController)
        }
    }
}

@Composable
fun HistoryGrid(dates: List<String>, steps: List<Int>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        item {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Date",
                        fontFamily = FontFamily.Serif,
                        fontSize = 23.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
                    dates.forEach() { i ->
                        Text(
                            text = i,
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
                Divider(
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Steps",
                        fontFamily = FontFamily.Serif,
                        fontSize = 23.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
                    steps.forEach { i ->
                        Text(
                            text = i.toString(),
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Goal",
                        fontFamily = FontFamily.Serif,
                        fontSize = 23.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )
                    steps.forEach { i ->
                        if (i > 5500) {
                            Icon(Icons.Filled.Check, contentDescription = "Done")
                        } else {
                            Icon(Icons.Filled.Close, contentDescription = "Not done")
                        }
                    }
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewHistory() {
    MovelsysTheme {
        HistoryScreenFragment(
            navController = rememberNavController(), viewModel = HistoryViewModel(
                googleFetchUseCase = GoogleFetchUseCase(
                    GoogleFetchDataImplementation()
                )
            )
        )
    }
}
