package com.example.movelsys.presentation_layer.activity_tracking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movelsys.R
import com.example.movelsys.Screen
import com.example.movelsys.ui.theme.MovelsysTheme


@Composable
fun TopBarFragment(navController: NavController) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.white_running_person),
                contentDescription = "MovElsys logo"
            )
        },
        title = { Text(text = "Movelsys", fontFamily = FontFamily.Serif, modifier = Modifier.padding(start = 0.dp)) },
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                Icon(Icons.Filled.Person, contentDescription = "Notification")
            }
        },

        )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovelsysTheme {
        TopBarFragment(navController = rememberNavController())
    }
}