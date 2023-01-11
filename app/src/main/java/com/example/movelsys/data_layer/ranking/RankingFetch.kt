package com.example.movelsys.data_layer.ranking

interface RankingFetch {
    var currentTeamRanking: String
    fun fetchCurrentUserTeam(): List<Person>
    fun fetchDesiredTeam(teamRank: Int): List<Person>
    fun fetchLeagueNumberOfTeams(): Int
}