package com.example.movelsys

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WelcomeScreenFragment(
    navController: NavController,
) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.running_person_no_background),
            contentDescription = "MovElsys Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(350.dp)
        )
        Text(text = "MovElsys", fontFamily = FontFamily.Serif, fontSize = 50.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(Screen.Login.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Login",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
        OutlinedButton(
            onClick = {
                navController.navigate(Screen.Register.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Register",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}
