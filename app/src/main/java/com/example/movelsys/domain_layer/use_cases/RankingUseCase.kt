package com.example.movelsys.domain_layer.use_cases

import com.example.movelsys.data_layer.ranking.RankingFetch

class RankingUseCase(
    private val rankingFetch: RankingFetch
) {
    fun fetchCurrentUserLeague() = rankingFetch.fetchLeagueName()
    fun fetchCurrentLeagueTeams() = rankingFetch.fetchCurrentLeagueTeams()
    fun fetchCurentLeagueSize() = rankingFetch.fetchLeagueNumberOfTeams()
}