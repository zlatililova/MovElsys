package com.example.movelsys.presentation_layer.activity_tracking.ranking

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movelsys.domain_layer.use_cases.RankingUseCase

class RankingViewModel(
    private val rankingUseCase: RankingUseCase
): ViewModel() {
    val currentLeagueTeams = mutableListOf<Pair<String, Int>>()
    var userNames: MutableList<String> = mutableListOf()
    var userProfilePictures: MutableList<String> = mutableListOf()
    var userWeeklySteps: MutableList<Int> = mutableListOf()


    fun fetchLeagueName(): String{
        return rankingUseCase.fetchCurrentUserLeague()
    }

    fun fetchCurrentLeagueTeams(){
            rankingUseCase.fetchCurrentLeagueTeams().forEach { (_, pair) ->
                Log.i("Team", pair.first)
                currentLeagueTeams.add(pair)
            }
        }

     fun getTeamDetails(teamRanking: Int) {
        val usersList = rankingUseCase.fetchDesiredTeam(teamRanking)
        usersList.forEach { person ->
            userNames.add(person.name)
            userProfilePictures.add(person.profilePicture)
            userWeeklySteps.add(person.totalWeeklySteps)
        }
    }

}