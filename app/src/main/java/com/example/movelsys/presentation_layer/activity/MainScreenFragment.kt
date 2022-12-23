package com.example.movelsys.presentation_layer

import android.app.Activity
import android.os.Build
import android.os.Handler
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
import com.example.movelsys.data_layer.google_fit.GoogleFitFetchData
import com.example.movelsys.data_layer.google_fit.GoogleFitPermissions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreenFragment() {
    val context = LocalContext.current
    val activity = context as Activity
    val googleFitFetchData = GoogleFitFetchData(context = context, activity = activity)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        GoogleFitPermissions(appContext = context, activity = activity).detectIfPermissionIsGiven()
    }
    Handler().postDelayed({
        googleFitFetchData.subscribeToStepsListener()
        googleFitFetchData.listActiveSubscriptions()
    }, 10000)

    googleFitFetchData.fetchPastWeekStepCount()

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
