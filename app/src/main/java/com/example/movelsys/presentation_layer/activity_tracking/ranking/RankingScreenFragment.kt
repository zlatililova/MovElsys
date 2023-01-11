package com.example.movelsys.presentation_layer.activity_tracking.ranking

import androidx.compose.foundation.Image
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
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment
import kotlin.math.roundToInt

@Composable
fun RankingScreenFragment(navController: NavController, viewModel: RankingViewModel) {
    viewModel.fetchTeamBasedOnSlider()
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    MotivationalMessage(viewModel = viewModel)
                    Text(
                        text = "You are viewing team in place:",
                        fontFamily = FontFamily.Serif,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    PlaceInRankList(viewModel = viewModel)
                }
                Column(modifier = Modifier.padding(20.dp)) {
                    Slider(
                        value = viewModel.sliderPosition,
                        onValueChange = {
                            viewModel.sliderPosition = it
                            viewModel.displayedTeam = it.roundToInt() + 1
                        },
                        valueRange = 0f..10f,
                        onValueChangeFinished = {
                            viewModel.fetchTeamBasedOnSlider()
                        },
                        steps = viewModel.getNumberOfTeamsInLeague() - 1
                    )
                }
                TableScreen(viewModel = viewModel)
            }
            Row {
                BottomBarFragment(navController = navController)
            }
        }
    }
}

@Composable
fun MotivationalMessage(viewModel: RankingViewModel) {
    when (viewModel.currentTeamRanking) {
        1 -> {
            Text(
                text = "Congratulations! Your team is doing great! Current team place in league: ${viewModel.currentTeamRanking}",
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
        2 -> {
            Text(
                text = "Good job! Keep up the good work! Current team place in league: ${viewModel.currentTeamRanking}",
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 20.dp)
            )

        }
        3 -> {
            Text(
                text = "You are doing great! Walk a little bit more today and climb the rank list! Current team place in league: ${viewModel.currentTeamRanking}",
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
        else -> {
            Text(
                text = "Take the stairs today! You still have time to perform better! Current team place in league: ${viewModel.currentTeamRanking}",
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 20.dp)
            )

        }
    }
}

@Composable
fun PlaceInRankList(viewModel: RankingViewModel) {
    when (viewModel.displayedTeam) {
        1 -> {
            MedalWidget(
                color = Color(0xFFFFD700),
                teamPosition = viewModel.displayedTeam.toString(),
                medalFontColor = Color.Black
            )
        }
        2 -> {
            MedalWidget(
                color = Color.LightGray,
                teamPosition = viewModel.displayedTeam.toString(),
                medalFontColor = Color.White
            )
        }
        3 -> {
            MedalWidget(
                color = Color(0xFFCD7F32),
                teamPosition = viewModel.displayedTeam.toString(),
                medalFontColor = Color.White
            )
        }
        else -> {
            MedalWidget(
                color = Color(0xFF3CB1FF),
                teamPosition = viewModel.displayedTeam.toString(),
                medalFontColor = Color.White
            )
        }
    }
}

@Composable
fun MedalWidget(color: Color, teamPosition: String, medalFontColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = this.size.maxDimension
                    )
                },
            text = teamPosition,
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
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
    if (type == "heading") {
        Text(
            text = text,
            fontFamily = FontFamily.Serif,
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
                .padding(8.dp)
        )
    }
}

@Composable
fun TableScreen(viewModel: RankingViewModel) {
    val profilePictureColumnWeight = .3f
    val nameColumnWeight = .4f
    val weeklyStepsColumnWeight = .3f
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TableCell(text = "Profile", weight = profilePictureColumnWeight, "heading")
            TableCell(text = "Name", weight = nameColumnWeight, "heading")
            TableCell(text = "Weekly steps", weight = weeklyStepsColumnWeight, "heading")
        }
        viewModel.userNames.forEachIndexed { index, name ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCell(
                    text = viewModel.userProfilePictures[index],
                    weight = profilePictureColumnWeight,
                    "picture"
                )
                TableCell(text = name, weight = nameColumnWeight, "text")
                TableCell(
                    text = viewModel.userWeeklySteps[index].toString(),
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