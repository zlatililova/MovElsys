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
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import java.util.concurrent.TimeUnit

class GoogleSensorDataImplementation : GoogleSensorData {
    private lateinit var activity: Activity
    private lateinit var context: Context
    private val fitnessOptions =
        FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_DELTA).build()
    private var addStepCount by mutableStateOf(0)
    private var currentStepCount by mutableStateOf(0)

    override fun getActivityAndContext(activity: Activity, context: Context) {
        this.activity = activity
        this.context = context
    }

    override fun listAvailableDataSources() {
        Fitness.getSensorsClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .findDataSources(
                DataSourcesRequest.Builder()
                    .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                    .setDataSourceTypes(DataSource.TYPE_RAW)
                    .build()
            )
            .addOnSuccessListener { dataSources ->
                dataSources.forEach {
                    Log.i(TAG, "Data source found: ${it.streamIdentifier}")
                    Log.i(TAG, "Data Source type: ${it.dataType.name}")

                    if (it.dataType == DataType.TYPE_STEP_COUNT_DELTA) {
                        Log.i(TAG, "Data source for STEP_COUNT_DELTA found!")
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Find data sources request failed", e)
            }
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
                Log.i("Daily Steps", totalSteps.toString())
            }
            .addOnFailureListener { e ->
                Log.i(TAG, "There was a problem getting steps.", e)
            }
    }

    override fun getCurrentSteps(): Int {
        return currentStepCount
    }
}