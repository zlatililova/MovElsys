package com.example.movelsys.presentation_layer

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movelsys.MainActivity
import com.example.movelsys.data_layer.google_fit.GoogleFitFetchData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
@Composable
fun MainScreenFragment() {
    val context = LocalContext.current
    GoogleFitFetchData(context = context, activity = context.find)

    val user = Firebase.auth.currentUser
    var text = ""
    if (user != null) {
        text = user.uid
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontSize = 20.sp
        )
    }
}