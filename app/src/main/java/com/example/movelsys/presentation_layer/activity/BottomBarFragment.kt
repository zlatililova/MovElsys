package com.example.movelsys.presentation_layer.activity

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.Screen
import com.example.movelsys.ui.theme.MovelsysTheme


@Composable
fun BottomBarFragment(
    navController: NavController,
){
    val tabData = listOf(
        "ACTIVITY" to Icons.Filled.Person to Screen.Activity.route,
        "RANKING" to Icons.Filled.CheckCircle to Screen.Ranking.route,
        "HISTORY" to Icons.Filled.Menu to Screen.History.route,

        )

    TabRow(selectedTabIndex = BottomBarHelper.getLocalTabIndex()) {
        tabData.forEachIndexed{ index, pair ->
            Log.i(TAG, "INDEX AFTER WHEN ${BottomBarHelper.getLocalTabIndex()}")
            Log.i(TAG, "TABNAME: ${BottomBarHelper.getLocalTabName()}")
            //Log.i(TAG, "Index of page: $index")
            //Log.i(TAG, "TabIndex: $tabIndex")
            Tab(selected = index == BottomBarHelper.getLocalTabIndex(), onClick = {
                BottomBarHelper.setLocalTabName(pair.second)

                navController.navigate(pair.second)
                when(BottomBarHelper.getLocalTabName()){
                    Screen.Activity.route -> BottomBarHelper.setLocalTabIndex(0)
                    Screen.Ranking.route -> BottomBarHelper.setLocalTabIndex(1)
                    Screen.History.route -> BottomBarHelper.setLocalTabIndex(2)
                }


            }, text = {
                Text(text = pair.first.first)
            }, icon = {
                Icon(imageVector = pair.first.second, contentDescription = null)
            })
            Log.i(TAG, "Index of page: $index")
            //Log.i(TAG, "TabIndex: $tabIndex")
        }

    }
}

object BottomBarHelper{
    private var tabIndex = 0
    private var tabName  = ""

    fun getLocalTabIndex(): Int {
        return tabIndex
    }
    fun setLocalTabIndex(index: Int) {
        tabIndex = index
    }
    fun getLocalTabName(): String {
        return tabName
    }
    fun setLocalTabName(name: String) {
        tabName = name
            }


}


@Preview(showBackground = true)
@Composable
fun DefaultPreviewBottom() {
    MovelsysTheme {
        BottomBarFragment(navController = rememberNavController())
    }
}