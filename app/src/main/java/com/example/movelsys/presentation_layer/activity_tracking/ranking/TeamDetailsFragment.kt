package com.example.movelsys.presentation_layer.activity_tracking.ranking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movelsys.presentation_layer.activity_tracking.BottomBarFragment
import com.example.movelsys.presentation_layer.activity_tracking.TopBarFragment

@Composable
fun TeamDetailsFragment(navController: NavController, viewModel: RankingViewModel, teamRank: Int) {
    viewModel.getTeamDetails(teamRank)
    Column{
        TopBarFragment(navController)

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ranking",
                        fontFamily = FontFamily.Serif,
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Text(
                        text = teamRank.toString(),
                        fontFamily = FontFamily.Serif,
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    DetailsGrid(viewModel = viewModel)
                }

            }
        }
        Row {
            BottomBarFragment(navController = navController)
        }
    }


    //DetailsGrid(viewModel = viewModel)
}

@Composable
fun DetailsGrid(viewModel: RankingViewModel) {
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
