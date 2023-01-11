package com.example.movelsys.data_layer.ranking

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RankingFetchImplementation() {
    private val dummyAPIToFetchData = DummyAPIToFetchData()
    lateinit var currentTeamRanking: String
    private var usersList = mutableListOf<Person>()

    fun fetchFromAPI(): List<Person>{
        val gson = Gson()
        usersList = gson.fromJson(
            dummyAPIToFetchData.fetchDataFromAPI(currentTeamRanking.toInt()),
            object : TypeToken<List<Person>>() {}.type
        )
        Log.e("List", usersList.toString())
        return usersList
    }

    fun getCurrentTeamRanking(index: Int){
        currentTeamRanking = index.toString()
    }
}