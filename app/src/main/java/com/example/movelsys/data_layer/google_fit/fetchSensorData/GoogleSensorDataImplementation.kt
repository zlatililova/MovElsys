package com.example.movelsys.data_layer.google_fit.fetchSensorData

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import java.util.concurrent.TimeUnit

class GoogleSensorDataImplementation : GoogleSensorData {
    private lateinit var activity: Activity
    private lateinit var context: Context
    private val fitnessOptions =
        FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_DELTA).build()
    private var currentStepCount by mutableStateOf(0)

    override fun getActivityAndContext(activity: Activity, context: Context) {
        this.activity = activity
        this.context = context
    }

    override fun addRawDataListener() {
        val listener = OnDataPointListener { dataPoint ->
            for (field in dataPoint.dataType.fields) {
                val value = dataPoint.getValue(field)
                Log.i(TAG, "Detected DataPoint field: ${field.name}")
                Log.i(TAG, "Detected DataPoint value: $value")
                getDailySteps()
            }
        }
        Fitness.getSensorsClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .add(
                SensorRequest.Builder()
                    .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                    .setSamplingRate(3, TimeUnit.SECONDS)
                    .build(),
                listener
            )
            .addOnSuccessListener {
                Log.i(TAG, "Listener registered!")
            }
            .addOnFailureListener {
                Log.e(TAG, "Listener not registered.", it)
            }
    }

    override fun getDailySteps() {
        Fitness.getHistoryClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { result ->
                val totalSteps =
                    result.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
                currentStepCount = totalSteps
            }
            .addOnFailureListener { e ->
                Log.i(TAG, "There was a problem getting steps.", e)
            }
    }

    override fun getCurrentSteps(): Int {
        return currentStepCount
    }
}