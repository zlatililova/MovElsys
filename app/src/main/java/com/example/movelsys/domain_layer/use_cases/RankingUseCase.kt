package com.example.movelsys.domain_layer.use_cases

import android.util.Log
import com.example.movelsys.data_layer.ranking.RankingFetch

class RankingUseCase(
    private val rankingFetch: RankingFetch
) {
    fun fetchCurrentUserLeague() = rankingFetch.fetchLeagueName()
    fun fetchCurrentLeagueTeams() = rankingFetch.fetchCurrentLeagueTeams()
    fun fetchDesiredTeam() = rankingFetch.fetchDesiredTeam()
    fun selectTeamRanking(teamRank: Int){
        Log.i("USECASE", teamRank.toString())

        rankingFetch.setSelectedTeamRanking(teamRank)
    }
}