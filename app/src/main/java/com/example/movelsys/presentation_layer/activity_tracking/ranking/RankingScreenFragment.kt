package com.example.movelsys.presentation_layer.activity_tracking.ranking

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.movelsys.Screen
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment
import kotlin.math.roundToInt

@Composable
fun RankingScreenFragment(navController: NavController, viewModel: RankingViewModel) {
    viewModel.fetchCurrentLeagueTeams()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBarFragment(navController, false)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${viewModel.fetchLeagueName()} League",
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Text(
                        text = "Ranking",
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TableScreen(viewModel = viewModel, navController = navController)
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
fun PlaceInRankList(place: Int) {
    when (place) {
        1 -> {
            MedalWidget(
                color = Color(0xFFFFD700),
                teamPosition = place.toString(),
                medalFontColor = Color.Black
            )
        }
        2 -> {
            MedalWidget(
                color = Color.LightGray,
                teamPosition = place.toString(),
                medalFontColor = Color.White
            )
        }
        3 -> {
            MedalWidget(
                color = Color(0xFFCD7F32),
                teamPosition = place.toString(),
                medalFontColor = Color.White
            )
        }
        else -> {
            MedalWidget(
                color = Color(0xFF3CB1FF),
                teamPosition = place.toString(),
                medalFontColor = Color.White
            )
        }
    }
}


@Composable
fun MedalWidget(color: Color, teamPosition: String, medalFontColor: Color) {
    var startPadding = 16.dp
    var allSidesPadding = 16.dp
    if (teamPosition.toInt() > 9) {
        startPadding = 10.dp
    }
    Column(modifier = Modifier.padding(start = 30.dp)) {
        Text(
            modifier = Modifier
                .padding(
                    top = allSidesPadding,
                    bottom = allSidesPadding,
                    end = allSidesPadding,
                    start = startPadding
                )
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = this.size.maxDimension
                    )
                },
            text = teamPosition,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = medalFontColor
        )
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    type: String
) {
    if (type == "medal") {
        PlaceInRankList(place = text.toInt())
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
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .weight(weight)
                .padding(start = 25.dp, end = 10.dp)
        )
    }
    if (type == "picture") {
        Box(
            modifier = Modifier
                .weight(weight)
                .padding(start = 30.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(text),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
        }
    }

}

@Composable
fun TableScreen(viewModel: RankingViewModel, navController: NavController) {
    val profilePictureColumnWeight = .3f
    val nameColumnWeight = .4f
    val weeklyStepsColumnWeight = .3f
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TableCell(text = "Ranking", weight = profilePictureColumnWeight, "heading")
            TableCell(text = "Team Name", weight = nameColumnWeight, "heading")
            TableCell(text = "Weekly steps", weight = weeklyStepsColumnWeight, "heading")
        }
        viewModel.currentLeagueTeams.forEachIndexed { index, pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable {
                        navController.navigate(Screen.TeamDetails.route + "/${index + 1}")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(
                    text = (index + 1).toString(),
                    weight = profilePictureColumnWeight,
                    type = "medal"
                )
                Log.i("Team name", pair.first)
                TableCell(text = pair.first, weight = nameColumnWeight, "text")
                TableCell(
                    text = pair.second.toString(),
                    weight = weeklyStepsColumnWeight,
                    "text"
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
