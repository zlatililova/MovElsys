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
import com.google.gson.Gson
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit

class GoogleFetchDataImplementation: GoogleFetchData {

    //private var dataPointsList = mutableListOf<DataPoint>()
    var dataPointMap = mutableMapOf<String, Int>()
    private lateinit var activity: Activity
    private lateinit var context: Context

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    override fun subscribeToStepsListener() {
        Fitness.getRecordingClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully subscribed to STEP_COUNT_DELTA!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem subscribing to STEP_COUNT_DELTA.", e)
            }

    }

    override fun listActiveSubscriptions() {
        Fitness.getRecordingClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
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

    override fun fetchPastWeekStepCount(responses: Responses) {
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
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(
                    startTime.toEpochSecond(), endTime.toEpochSecond(),
                    TimeUnit.SECONDS
                )
                .build()

        Fitness.getHistoryClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    Log.i(TAG, "DATA SET")
                    dumpDataSet(dataSet)
                }
                checkDataList()
                responses.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was an error reading data from Google Fit", e)
                responses.onError("There was an error reading data from Google Fit")
            }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        for (dp in dataSet.dataPoints) {
            var startTimeMillis = dp.getStartTimeString()
            //var date = Date(startTimeMillis)
            val field = dp.dataType.fields[0]
            val steps = dp.getValue(field).asInt()
            dataPointMap.put(startTimeMillis,steps)
            //dataPointsList.add(dp)
        }

    }

    fun DataPoint.getStartTimeString() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
                .atZone(ZoneId.systemDefault())
                .toLocalDate().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

    fun DataPoint.getEndTimeString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    private fun checkDataList() {
        Log.i(TAG, "DATA POINT LIST SIZE " + dataPointMap.size.toString())
        for(i in dataPointMap){
            Log.i(TAG, "Date: " + i.key)
            Log.i(TAG, "Steps: " + i.value)
        }
    }

    override fun getActivityandContext(activity: Activity, context: Context){
        this.activity = activity
        this.context = context
    }

    override fun getDataPointList(): String {
        Log.i(TAG, "DATA POINT LIST SIZE " + dataPointMap.size.toString())
        val gson = Gson()
        val json = gson.toJson(dataPointMap)
        Log.i(TAG, json.toString())
        return json

    }

}