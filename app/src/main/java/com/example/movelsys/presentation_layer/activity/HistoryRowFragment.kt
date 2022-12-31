package com.example.movelsys.presentation_layer.activity

import android.os.Build
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.android.gms.fitness.data.DataPoint
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit

@Composable
fun HistoryRowFragment(datapoint: Map.Entry<String, Int>){
    Row{
        Text(text = "LALALA", fontFamily = FontFamily.Serif, fontSize = 5.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
        //Text(text = datapoint.getStartTimeString(), fontFamily = FontFamily.Serif, fontSize = 5.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
    }
}

fun DataPoint.getStartTimeString() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
