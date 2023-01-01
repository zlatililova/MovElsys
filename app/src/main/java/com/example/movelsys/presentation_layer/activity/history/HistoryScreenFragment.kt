package com.example.movelsys.presentation_layer

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.movelsys.presentation_layer.activity.BottomBarFragment
import com.example.movelsys.presentation_layer.activity.history.HistoryViewModel
import com.example.movelsys.presentation_layer.activity.history.HistoryRowFragment
import com.example.movelsys.presentation_layer.activity.TopBarFragment
import kotlinx.coroutines.delay

@Composable
fun HistoryScreenFragment(
    navController: NavController,
    viewModel: HistoryViewModel
) {

    val context = LocalContext.current
    val activity = context as Activity
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TopBarFragment()
            viewModel.startGoogleFit(context, activity)
            //HistoryList(dates = viewModel.googleFitDates, steps = viewModel.googleFitSteps)
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }


}

@Composable
fun HistoryList(dates: List<String>, steps: List<Int>) {
    LazyColumn {
        Log.i(TAG, "Dates size: ${dates.size}")
       /* var counter1 = 0
        while(counter1< 7){
                Log.i(TAG, "Dates size: ${dates[counter1]}")
            counter1++
        }*/

        items(7) { counter ->
            Log.i(TAG, "Counter: $counter")
            HistoryRowFragment(date = dates[counter], steps = steps[counter])
        }
    }
}


//{"2022-12-25":11368,"2022-12-26":13969,"2022-12-27":14901,"2022-12-28":4081,"2022-12-29":300,"2022-12-30":717,"2022-12-31":1071}


