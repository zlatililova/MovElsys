package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.ranking.RankingFetch

class RankingUseCase(
    private val rankingFetch: RankingFetch
) {
    fun fetchCurrentUserTeam() = rankingFetch.fetchCurrentUserTeam()
    fun fetchDesiredTeam(teamRank: Int) = rankingFetch.fetchDesiredTeam(teamRank)
    fun fetchCurrentTeamRanking() = rankingFetch.currentTeamRanking
    fun fetchTeamCountInLeague() = rankingFetch.fetchLeagueNumberOfTeams()
}