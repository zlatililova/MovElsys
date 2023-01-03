package com.example.movelsys.data_layer.google_fit

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
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import java.util.concurrent.TimeUnit

class GoogleSensorDataImplementation {

    private lateinit var activity: Activity
    private lateinit var context: Context
    private val fitnessOptions = FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_DELTA).build()
    private var addStepCount by mutableStateOf(0)

    fun getActivityAndContext(activity: Activity, context: Context){
        this.activity = activity
        this.context = context
    }

    fun listAvailableDataSources(){
        Fitness.getSensorsClient(activity, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            .findDataSources(
                DataSourcesRequest.Builder()
                    .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                    .setDataSourceTypes(DataSource.TYPE_RAW)
                    .build())
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

    fun addRawDataListener(){
        val listener = OnDataPointListener { dataPoint ->
            for (field in dataPoint.dataType.fields) {
                val value = dataPoint.getValue(field)
                addStepCount += value.asInt()
                Log.i(TAG, "Detected DataPoint field: ${field.name}")
                Log.i(TAG, "Detected DataPoint value: $addStepCount")

            }
        }
        Fitness.getSensorsClient(activity, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            .add(
                SensorRequest.Builder()
                   // .setDataSource(listAvailableDataSources()) // Optional but recommended for custom
                    // data sets.
                    .setDataType(DataType.TYPE_STEP_COUNT_DELTA) // Can't be omitted.
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

    fun getAddStepValue(): Int{
        return addStepCount
    }
}