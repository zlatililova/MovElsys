package com.example.movelsys.data_layer.ranking

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

//this whole class is a mock of an API. The concept of the project is to connect to a real API, created as a diploma paper by another student at ELSYS
class DummyAPIToFetchData {

    private fun fetchCurrentUserTeam(uid: String): String {
        //fetch json with the people of current user's group
        val user = Firebase.auth.currentUser
        val photo = user?.photoUrl.toString()

        val person1 = Person(
            name = "Ivan Ivanov",
            profilePicture = "https://as1.ftcdn.net/v2/jpg/00/64/67/52/1000_F_64675209_7ve2XQANuzuHjMZXP3aIYIpsDKEbF5dD.jpg",
            totalWeeklySteps = 35670
        )
        val person2 = Person(
            name = "Hristo Petrov",
            profilePicture = "https://www.graphicpie.com/wp-content/uploads/2020/11/cyan-among-us-character.png",
            totalWeeklySteps = 33857
        )
        val person3 = Person(
            name = "Grigor Hristov",
            profilePicture = "https://www.graphicpie.com/wp-content/uploads/2020/11/among-us-green-png.png, ",
            totalWeeklySteps = 30659
        )
        val person4 =
            user?.displayName?.let { Person(name = it, profilePicture = photo, totalWeeklySteps = 28854) }
        val person5 = Person(
            name = "Ivanina Georgieva",
            profilePicture = "https://w7.pngwing.com/pngs/396/737/png-transparent-infj-personality-type-myers-briggs-type-indicator-personality-test-infj-fictional-character-individual-advocate.png",
            totalWeeklySteps = 25250
        )
        val dummyJSON = listOf(person1, person2, person3, person4, person5)
        val gson = Gson()
        return gson.toJson(dummyJSON)

    }

    fun fetchCurrentUserTeamRanking(uid: String): Int {
        //this function simulates a fetch of current user's team ranking
        return 2
    }

    fun fetchLeagueNumberOfTeams(league: String): Int {
        return 10
    }

    fun fetchCurrentUserLeague(uid: String): String {
        return "Golden"
    }

    fun fetchCurrentLeagueTeams(league: String): String{
        var mapOfTeams = mutableMapOf<Int, Pair<String, Int>>()
        mapOfTeams[1] = Pair("Soccer Stars", 65054)
        mapOfTeams[2] = Pair("Amazing team of 5", 54054)
        mapOfTeams[3] = Pair("The invincible", 63154)
        mapOfTeams[4] = Pair("Walk it 'till you make it", 62254)
        mapOfTeams[5] = Pair("Dream team No.1", 61054)
        mapOfTeams[6] = Pair("Fitness maniacs", 60054)
        mapOfTeams[7] = Pair("A bunch of walkers", 60054)
        mapOfTeams[8] = Pair("Fabulously fit", 59054)
        mapOfTeams[9] = Pair("Golden team", 58054)
        mapOfTeams[10] = Pair("Nothing stops us", 57054)
        val gson = Gson()
        return gson.toJson(mapOfTeams)
    }

    fun fetchDesiredTeam(teamRanking: Int, league: String): String {
        //the idea is to fetch data from Kalin Georgiev's API
        //this class is a mock function from K.Georgiev's API that returns a JSON with the team on the teamRanking page
        if(teamRanking == 2){
            return fetchCurrentUserTeam("0")
        }
        if(teamRanking == 1){
            val person1 = Person(
                name = "John Doe",
                profilePicture = "https://as1.ftcdn.net/v2/jpg/00/64/67/52/1000_F_64675209_7ve2XQANuzuHjMZXP3aIYIpsDKEbF5dD.jpg",
                totalWeeklySteps = 35670
            )
            val dummyJSON = listOf(person1, person1, person1, person1, person1, person1)
            val gson = Gson()
            return gson.toJson(dummyJSON)
        }
        if(teamRanking == 3){
            val person1 = Person(
                name = "Jane Doe",
                profilePicture = "https://www.graphicpie.com/wp-content/uploads/2020/11/cyan-among-us-character.png",
                totalWeeklySteps = 33857
            )
            val dummyJSON = listOf(person1, person1, person1, person1, person1, person1)
            val gson = Gson()
            return gson.toJson(dummyJSON)
        }
        if(teamRanking == 4){
            val person1 = Person(
                name = "Maria Ivanova",
                profilePicture = "https://www.graphicpie.com/wp-content/uploads/2020/11/among-us-green-png.png, ",
                totalWeeklySteps = 30659
            )
            val dummyJSON = listOf(person1, person1, person1, person1, person1, person1)
            val gson = Gson()
            return gson.toJson(dummyJSON)
        }
        if(teamRanking == 5){
            val person1 = Person(
                name = "Dimitrichka Georgieva",
                profilePicture = "https://w7.pngwing.com/pngs/396/737/png-transparent-infj-personality-type-myers-briggs-type-indicator-personality-test-infj-fictional-character-individual-advocate.png",
                totalWeeklySteps = 25250
            )
            val dummyJSON = listOf(person1, person1, person1, person1, person1, person1)
            val gson = Gson()
            return gson.toJson(dummyJSON)
        }
        else{
            val person1 = Person(
                name = "Georgi Panchev",
                profilePicture = "https://png.pngitem.com/pimgs/s/22-220435_transparent-history-icon-png-iconos-png-inventario-png.png ",
                totalWeeklySteps = 28854
            )
            val dummyJSON = listOf(person1, person1, person1, person1, person1, person1)
            val gson = Gson()
            return gson.toJson(dummyJSON)
        }

    }

}
