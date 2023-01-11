package com.example.movelsys.presentation_layer.activity_tracking.ranking

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movelsys.data_layer.ranking.RankingFetchImplementation

class RankingViewModel {
    var userNames: MutableList<String>  = mutableListOf()
    var userProfilePictures: MutableList<String> = mutableListOf()
    var userWeeklySteps: MutableList<Int> = mutableListOf()
    var teamRanking: Int by mutableStateOf(0)

    private val rankingFetchImplementation = RankingFetchImplementation()
    fun getUsersList(){
        rankingFetchImplementation.getCurrentTeamRanking(4)
        val usersList = rankingFetchImplementation.fetchFromAPI()
        usersList.forEach{ person ->
            Log.e("Person", person.toString())
            userNames.add(person.name)
            userProfilePictures.add(person.profilePicture)
            userWeeklySteps.add(person.totalWeeklySteps)
        }
    }

    fun getTeamPosition():String{
        teamRanking = rankingFetchImplementation.currentTeamRanking.toInt()
        return rankingFetchImplementation.currentTeamRanking
    }
}