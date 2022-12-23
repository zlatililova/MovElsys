package com.example.movelsys.data_layer.google_fit

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class GoogleFitFetchData(
    val activity: Activity,
    val context: Context
) {
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    fun subscribeToStepsListener(){
        Fitness.getRecordingClient(activity, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully subscribed to STEP_COUNT_DELTA!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem subscribing to STEP_COUNT_DELTA.", e)
            }

    }

    fun listActiveSubscriptions(){
        Fitness.getRecordingClient(activity, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            .listSubscriptions()
            .addOnSuccessListener { subscriptions ->
                for (sc in subscriptions) {
                    val dt = sc.dataType
                    if (dt != null) {
                        Log.i(TAG, "Active subscription for data type: ${dt.name}")
                    }
                }
            }
    }

    fun fetchPastWeekStepCount(){
        // Read the data that's been collected throughout the past week.
        val endTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().atZone(ZoneId.systemDefault())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val startTime = endTime.minusWeeks(1)
        Log.i(TAG, "Range Start: $startTime")
        Log.i(TAG, "Range End: $endTime")

        val readRequest =
            DataReadRequest.Builder()
                // The data request can specify multiple data types to return,
                // effectively combining multiple data queries into one call.
                // This example demonstrates aggregating only one data type.
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be
                // aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession allows
                // bucketing by <a href="/fit/android/using-sessions">sessions</a>.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(),
                    TimeUnit.SECONDS
                )
                .build()

        Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            .readData(readRequest)
            .addOnSuccessListener { response ->
                // The aggregate query puts datasets into buckets, so flatten into a
                // single list of datasets
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    dumpDataSet(dataSet)
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG,"There was an error reading data from Google Fit", e)
            }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i(TAG,"Data point:")
            Log.i(TAG,"\tType: ${dp.dataType.name}")
            Log.i(TAG,"\tStart: ${dp.getStartTimeString()}")
            Log.i(TAG,"\tEnd: ${dp.getEndTimeString()}")
            for (field in dp.dataType.fields) {
                Log.i(TAG,"\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
            }
        }
    }

    private fun DataPoint.getStartTimeString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    private fun DataPoint.getEndTimeString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
}