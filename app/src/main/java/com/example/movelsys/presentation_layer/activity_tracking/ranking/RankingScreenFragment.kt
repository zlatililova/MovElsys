package com.example.movelsys.presentation_layer.activity_tracking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryGrid
import com.example.movelsys.presentation_layer.activity_tracking.history.HistoryRow
import com.example.movelsys.presentation_layer.activity_tracking.ranking.RankingViewModel

@Composable
fun RankingScreenFragment(navController: NavController, viewModel: RankingViewModel){
    viewModel.getUsersList()
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarFragment(navController)
            Text(
                text = "Ranking",
                fontFamily = FontFamily.Serif,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 15.dp)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Box{
                RankingMedal(viewModel = viewModel)
            }
            TableScreen(viewModel = viewModel)

        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }

}

@Composable
fun RankingMedal(viewModel: RankingViewModel) {
    when(viewModel.teamRanking){
        1 -> {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.Yellow,
                        radius = this.size.maxDimension
                    )
                },
            text = viewModel.getTeamPosition(),
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        }
        2 -> {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.LightGray,
                            radius = this.size.maxDimension
                        )
                    },
                text = viewModel.getTeamPosition(),
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        3 -> {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color(0xFFCD7F32),
                            radius = this.size.maxDimension
                        )
                    },
                text = viewModel.getTeamPosition(),
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        else -> {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color(0xFF3CB1FF),
                            radius = this.size.maxDimension
                        )
                    },
                text = viewModel.getTeamPosition(),
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }


}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    type: String
) {
    if(type == "picture"){
        Box(modifier = Modifier.weight(weight).padding(start = 30.dp)){
            Image(
                painter = rememberAsyncImagePainter(text),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
        }
    }
    if(type == "heading"){
        Text(
            text = text,
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 15.dp).weight(weight)
        )
    }
    if(type=="text"){
        Text(
            text = text,
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
fun TableScreen(viewModel: RankingViewModel) {
    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .4f // 40%
    val column3Weight = .3f // 30%

    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        // Here is the header
        item {
            Row (verticalAlignment = Alignment.CenterVertically){
                TableCell(text = "Profile", weight = column1Weight, "heading")
                TableCell(text = "Name", weight = column2Weight, "heading")
                TableCell(text = "Weekly steps", weight = column3Weight, "heading")

            }
        }
        // Here are all the lines of your table.
        item {
            viewModel.userNames.forEachIndexed{ index, name ->
                Row(modifier = Modifier.fillMaxWidth(). padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    TableCell(text = viewModel.userProfilePictures[index], weight = column1Weight, "picture")
                    TableCell(text = name, weight = column2Weight, "text")
                    TableCell(text = viewModel.userWeeklySteps[index].toString(), weight = column3Weight, "text")
                }
                Divider(color = MaterialTheme.colors.primary, thickness = 2.dp, modifier = Modifier.padding(bottom = 20.dp))

            }
        }
    }
}