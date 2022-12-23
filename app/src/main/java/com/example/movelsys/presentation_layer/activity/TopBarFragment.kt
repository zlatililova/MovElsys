package com.example.movelsys.presentation_layer.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movelsys.R
import com.example.movelsys.ui.theme.MovelsysTheme


@Composable
fun TopBarFragment() {
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notification")
            }
        },

        )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovelsysTheme {
        TopBarFragment()
    }
}