package com.example.movelsys.presentation_layer.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movelsys.R
import com.example.movelsys.ui.theme.MovelsysTheme


@Composable
fun topBarFragment(){
    TopAppBar(backgroundColor = MaterialTheme.colors.primary,
    contentColor = Color.White,
        navigationIcon = {Image(painter = painterResource(id = R.drawable.running_person_no_background), contentDescription = "MovElsys logo")},
    title = { Text(text = "Movelsys", modifier = Modifier.padding(start = 80.dp)) },
    actions = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Person, contentDescription = "Profile")
        }
    },

    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovelsysTheme {
        topBarFragment()
    }
}