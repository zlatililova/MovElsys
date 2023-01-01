package com.example.movelsys.presentation_layer.activity_tracking.history

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
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
    Row(horizontalArrangement  =  Arrangement.SpaceEvenly){

        Log.i(TAG, "ROW date & steps: $date $steps")

        Text(text = date, fontFamily = FontFamily.Serif, fontSize = 25.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
        Spacer(modifier = Modifier.padding(horizontal = 50.dp))
        Text(text = steps.toString(), fontFamily = FontFamily.Serif, fontSize = 25.sp, textAlign = TextAlign.Center, color = MaterialTheme.colors.primary )
    }
         
}
