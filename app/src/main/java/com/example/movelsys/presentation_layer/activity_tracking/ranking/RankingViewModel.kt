package com.example.movelsys.presentation_layer.activity_tracking.ranking

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movelsys.domain_layer.use_cases.RankingUseCase

class RankingViewModel(
    private val rankingUseCase: RankingUseCase
) {
    var userNames: MutableList<String> = mutableListOf()
    var userProfilePictures: MutableList<String> = mutableListOf()
    var userWeeklySteps: MutableList<Int> = mutableListOf()
    var displayedTeam: Int by mutableStateOf(rankingUseCase.fetchCurrentTeamRanking().toInt())
    var currentTeamRanking: Int by mutableStateOf(rankingUseCase.fetchCurrentTeamRanking().toInt())
    var sliderPosition: Float by mutableStateOf(currentTeamRanking.toFloat() - 1)

    private fun getCurrentUserTeam() {
        val usersList = rankingUseCase.fetchCurrentUserTeam()
        usersList.forEach { person ->
            userNames.add(person.name)
            userProfilePictures.add(person.profilePicture)
            userWeeklySteps.add(person.totalWeeklySteps)
        }
    }

    private fun getOtherTeam(teamRank: Int) {
        val usersList = rankingUseCase.fetchDesiredTeam(teamRank)
        usersList.forEach { person ->
            userNames.add(person.name)
            userProfilePictures.add(person.profilePicture)
            userWeeklySteps.add(person.totalWeeklySteps)
        }
    }

    fun fetchTeamBasedOnSlider() {
        userNames.clear()
        userProfilePictures.clear()
        userWeeklySteps.clear()
        if (displayedTeam == rankingUseCase.fetchCurrentTeamRanking().toInt()) {
            getCurrentUserTeam()
        } else {
            getOtherTeam(displayedTeam)
        }
    }

    fun getNumberOfTeamsInLeague(): Int {
        return rankingUseCase.fetchTeamCountInLeague()
    }
}