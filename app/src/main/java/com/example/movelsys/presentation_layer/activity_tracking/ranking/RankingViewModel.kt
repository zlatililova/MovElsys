package com.example.movelsys.presentation_layer.activity_tracking.ranking

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movelsys.domain_layer.use_cases.RankingUseCase

class RankingViewModel(
    private val rankingUseCase: RankingUseCase
) {
    val currentLeagueTeams = mutableListOf<Pair<String, Int>>()

    fun fetchLeagueName(): String{
        return rankingUseCase.fetchCurrentUserLeague()
    }

    fun fetchCurrentLeagueTeams(){
            rankingUseCase.fetchCurrentLeagueTeams().forEach { (_, pair) ->
                Log.i("Team", pair.first)
                currentLeagueTeams.add(pair)
            }
        }
}