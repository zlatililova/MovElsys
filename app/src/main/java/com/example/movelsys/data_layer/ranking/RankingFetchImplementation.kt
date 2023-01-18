package com.example.movelsys.data_layer.ranking

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RankingFetchImplementation : RankingFetch {
    private val dummyAPIToFetchData = DummyAPIToFetchData()
    private var currentTeamLeague: String
    private var usersList = mutableListOf<Person>()
    private val user = Firebase.auth.currentUser
    override var currentTeamRanking: String = ""

    init {
        currentTeamLeague = user?.let { dummyAPIToFetchData.fetchCurrentUserLeague(it.uid) }.toString()
        currentTeamRanking =
            user?.let { dummyAPIToFetchData.fetchCurrentUserTeamRanking(it.uid).toString() }.toString()
    }

    override fun fetchCurrentUserTeam(): List<Person> {
        val gson = Gson()
        usersList = gson.fromJson(
            user?.let { dummyAPIToFetchData.fetchCurrentUserTeam(it.uid) },
            object : TypeToken<List<Person>>() {}.type
        )
        return usersList
    }

    override fun fetchDesiredTeam(teamRank: Int): List<Person> {
        val gson = Gson()
        usersList = gson.fromJson(
            dummyAPIToFetchData.fetchDesiredTeam(teamRank, currentTeamLeague),
            object : TypeToken<List<Person>>() {}.type
        )
        return usersList
    }

    override fun fetchLeagueNumberOfTeams(): Int {
        return dummyAPIToFetchData.fetchLeagueNumberOfTeams(currentTeamLeague)
    }
}