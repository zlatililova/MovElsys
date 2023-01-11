package com.example.movelsys.data_layer.ranking

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject

class DummyAPIToFetchData {
    fun fetchDataFromAPI(teamRanking: Int): String {
        //the idea is to fetch data from Kalin Georgiev's API
        //this class is a mock function from K.Georgiev's API that returns a JSON with the team on the teamRanking page

        val person1 = Person(name ="John Doe", profilePicture = "https://as1.ftcdn.net/v2/jpg/00/64/67/52/1000_F_64675209_7ve2XQANuzuHjMZXP3aIYIpsDKEbF5dD.jpg",totalWeeklySteps = 35670)
        val person2 = Person(name ="Jane Doe", profilePicture = "https://www.graphicpie.com/wp-content/uploads/2020/11/cyan-among-us-character.png",totalWeeklySteps = 33857)
        val person3 = Person(name ="Maria Ivanova", profilePicture = "https://www.graphicpie.com/wp-content/uploads/2020/11/among-us-green-png.png, ",totalWeeklySteps = 30659)
        val person4 = Person(name ="Georgi Petrov", profilePicture = "https://png.pngitem.com/pimgs/s/22-220435_transparent-history-icon-png-iconos-png-inventario-png.png ",totalWeeklySteps = 28854)
        val person5 = Person(name ="Ivanininia Georgieva", profilePicture = "https://w7.pngwing.com/pngs/396/737/png-transparent-infj-personality-type-myers-briggs-type-indicator-personality-test-infj-fictional-character-individual-advocate.png",totalWeeklySteps = 25250)

        val dummyJSON = listOf(person1, person2, person3, person4, person5)
    val gson = Gson()
    val json = gson.toJson(dummyJSON)
    Log.i(TAG, json.toString())
    return json
    }
}
