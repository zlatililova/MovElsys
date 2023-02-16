package com.example.movelsys.presentation_layer.activity_tracking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.movelsys.R
import com.example.movelsys.Screen
import com.example.movelsys.ui.theme.MovelsysTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun TopBarFragment(navController: NavController, changeButton: Boolean) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        navigationIcon = {
            if (!changeButton) {
                Image(
                    painter = painterResource(id = R.drawable.white_running_person),
                    contentDescription = "MovElsys logo"
                )
            } else {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, "Pop backstack")
                }
            }
        },
        title = { Text(text = "Movelsys", modifier = Modifier.padding(start = 0.dp)) },
        actions = {
            Text(
                text = Firebase.auth.currentUser?.displayName.toString(),
                modifier = Modifier.padding(end = 10.dp)
            )
            IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                Image(
                    painter = rememberAsyncImagePainter(Firebase.auth.currentUser?.photoUrl),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                )
            }
        },
        )
}