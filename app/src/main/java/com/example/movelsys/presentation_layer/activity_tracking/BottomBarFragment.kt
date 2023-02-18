package com.example.movelsys.presentation_layer.activity_tracking

import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.Screen
import com.example.movelsys.ui.theme.MovelsysTheme


@Composable
fun BottomBarFragment(
    navController: NavController,
) {
    val tabData = listOf(
        "ACTIVITY" to Icons.Filled.EmojiPeople to Screen.Activity.route,
        "RANKING" to Icons.Filled.EmojiEvents to Screen.Ranking.route,
        "HISTORY" to Icons.Filled.AccessTime to Screen.History.route,
    )
    TabRow(selectedTabIndex = BottomBarHelper.getLocalTabIndex()) {
        tabData.forEachIndexed { index, pair ->
            Tab(selected = checkIndexOfHighlightedTab(index), onClick = {
                BottomBarHelper.setLocalTabName(pair.second)
                navController.navigate(pair.second)
                BottomBarHelper.setLocalTabIndex(index)
            }, text = {
                Text(text = pair.first.first)
            }, icon = {
                Icon(imageVector = pair.first.second, contentDescription = null)
            })
        }
    }
}

object BottomBarHelper {
    private var tabIndex = 0
    private var tabName = ""

    fun getLocalTabIndex(): Int {
        return tabIndex
    }

    fun setLocalTabIndex(index: Int) {
        tabIndex = index
    }

    fun setLocalTabName(name: String) {
        tabName = name
    }
}

fun checkIndexOfHighlightedTab(index: Int): Boolean {
    return index == BottomBarHelper.getLocalTabIndex()
}