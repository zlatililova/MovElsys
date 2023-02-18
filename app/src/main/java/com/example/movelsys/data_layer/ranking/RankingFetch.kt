package com.example.movelsys.data_layer.ranking

interface RankingFetch {
    var currentTeamRanking: String
    fun fetchDesiredTeam(ranking: Int): List<Person>
    fun fetchLeagueNumberOfTeams(): Int
    fun fetchLeagueName(): String
    fun fetchCurrentLeagueTeams(): Map<Int, Pair<String, Int>>
}