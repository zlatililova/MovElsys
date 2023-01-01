package com.example.movelsys.presentation_layer.activity.history

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryRowFragment(date: String, steps: Int){
    Row{
        /*Text(text = date, fontFamily = FontFamily.Serif, fontSize = 5.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
        Text(text = steps.toString(), fontFamily = FontFamily.Serif, fontSize = 5.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
        */
        Log.i(TAG, "ROW date & steps: $date $steps")

        Text(text = "LALALALLA")
        Text(text = date, fontFamily = FontFamily.Serif, fontSize = 25.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(text = steps.toString(), fontFamily = FontFamily.Serif, fontSize = 25.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )

    }
         
}
