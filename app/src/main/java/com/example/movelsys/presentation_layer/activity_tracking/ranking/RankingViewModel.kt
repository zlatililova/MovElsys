package com.example.movelsys.presentation_layer.activity_tracking.ranking

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movelsys.domain_layer.use_cases.RankingUseCase

class RankingViewModel(
    private val rankingUseCase: RankingUseCase
) {
    val currentLeagueTeams = mutableListOf<Pair<String, Int>>()
    var userNames: MutableList<String> = mutableListOf()
    var userProfilePictures: MutableList<String> = mutableListOf()
    var userWeeklySteps: MutableList<Int> = mutableListOf()
    var detailedTeamRanking: Int by mutableStateOf(1)


    fun fetchLeagueName(): String{
        return rankingUseCase.fetchCurrentUserLeague()
    }

    fun fetchCurrentLeagueTeams(){
            rankingUseCase.fetchCurrentLeagueTeams().forEach { (_, pair) ->
                Log.i("Team", pair.first)
                currentLeagueTeams.add(pair)
            }
        }

     fun getTeamDetails() {
         Log.i("VMODEL", detailedTeamRanking.toString())
        val usersList = rankingUseCase.fetchDesiredTeam()
        usersList.forEach { person ->
            userNames.add(person.name)
            userProfilePictures.add(person.profilePicture)
            userWeeklySteps.add(person.totalWeeklySteps)
        }
    }

    fun selectIndexofDetailedTeam(teamRanking: Int){
        Log.i("VMODEL", teamRanking.toString())
        rankingUseCase.selectTeamRanking(teamRanking)
    }

}