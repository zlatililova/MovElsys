package com.example.movelsys.presentation_layer

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.movelsys.data_layer.google_fit.GoogleFetchDataImplementation
import com.example.movelsys.domain_layer.use_cases.GoogleFetchUseCase
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryViewModel
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryRowFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment
import com.example.movelsys.ui.theme.MovelsysTheme

@Composable
fun HistoryScreenFragment(
    navController: NavController,
    viewModel: HistoryViewModel
) {

    val context = LocalContext.current
    val activity = context as Activity
    if(!viewModel.isFetchFinished){
        viewModel.startGoogleFit(context, activity)

    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBarFragment()
        Text(
            text = "History",
            fontFamily = FontFamily.Serif,
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.padding(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            HistoryGrid2(dates = viewModel.googleFitDates, steps =viewModel.googleFitSteps )
        }

        Row {
            BottomBarFragment(navController = navController)
        }
    }
    }

@Composable
fun HistoryGrid2(dates: List<String>, steps: List<Int>){
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){
        item{
            Row(horizontalArrangement = Arrangement.SpaceEvenly){
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Date", fontFamily = FontFamily.Serif, fontSize = 23.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
                    dates.forEach(){ i->
                        Text(text = i, fontFamily = FontFamily.Serif, fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
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
                    Text(text = "Steps", fontFamily = FontFamily.Serif, fontSize = 23.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
                    steps.forEach{ i->
                        Text(text = i.toString(), fontFamily = FontFamily.Serif, fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Performance", fontFamily = FontFamily.Serif, fontSize = 23.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
                    steps.forEach{ i->
                        if(i > 5500){
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Done")
                        }else{
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
            HistoryScreenFragment(navController = rememberNavController(), viewModel = HistoryViewModel(googleFetchUseCase = GoogleFetchUseCase(GoogleFetchDataImplementation())))
    }
}
//{"2022-12-25":11368,"2022-12-26":13969,"2022-12-27":14901,"2022-12-28":4081,"2022-12-29":300,"2022-12-30":717,"2022-12-31":1071}


