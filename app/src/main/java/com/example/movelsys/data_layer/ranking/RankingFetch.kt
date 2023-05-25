package com.example.movelsys.data_layer.ranking

import com.example.movelsys.data_layer.models.Person

interface RankingFetch {
    var currentTeamRanking: String
    fun fetchDesiredTeam(ranking: Int): List<Person>
    fun fetchLeagueNumberOfTeams(): Int
    fun fetchLeagueName(): String
    fun fetchCurrentLeagueTeams(): Map<Int, Pair<String, Int>>
}