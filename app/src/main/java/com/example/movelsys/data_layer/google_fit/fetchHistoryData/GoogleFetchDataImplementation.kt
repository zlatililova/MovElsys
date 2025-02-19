package com.example.movelsys.data_layer.google_fit.fetchHistoryData

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.movelsys.data_layer.google_fit.Responses
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.gson.Gson
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class GoogleFetchDataImplementation : GoogleFetchData {
    private var dataPointMap = mutableMapOf<String, Int>()
    private lateinit var activity: Activity
    private lateinit var context: Context
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    override var isUserSubscribedToStepsListener = false

    override fun subscribeToStepsListener() {
        Fitness.getRecordingClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully subscribed to STEP_COUNT_DELTA!")
                isUserSubscribedToStepsListener = true
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem subscribing to STEP_COUNT_DELTA.", e)
            }
    }

    override fun fetchPastMonthStepCount(responses: Responses) {
        // Read the data that's been collected throughout the past 4 weeks.
        val endTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().atZone(ZoneId.systemDefault()).minusHours(10)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val startTime = endTime.minusWeeks(4)
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(
                startTime.toEpochSecond(), endTime.toEpochSecond(),
                TimeUnit.SECONDS
            ).build()
        Fitness.getHistoryClient(
            activity,
            GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        )
            .readData(readRequest)
            .addOnSuccessListener { response ->
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    dumpDataSet(dataSet)
                    Log.i(TAG,"Succesfully read from history")
                }
                responses.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was an error reading data from Google Fit", e)
                responses.onError("There was an error reading data from Google Fit")
            }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        for (dp in dataSet.dataPoints) {
            val startTimeMillis = changeDateSignature(dp.getStartTimeString())
            val field = dp.dataType.fields[0]
            val steps = dp.getValue(field).asInt()
            dataPointMap[startTimeMillis] = steps
        }
    }

    private fun DataPoint.getStartTimeString() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
                .atZone(ZoneId.systemDefault())
                .toLocalDate().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

    override fun getActivityAndContext(activity: Activity, context: Context) {
        this.activity = activity
        this.context = context
    }

    override fun getDataPointList(): String {
        val gson = Gson()
        return gson.toJson(dataPointMap)
    }

    private fun changeDateSignature(string_date: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("MMM dd")
            val date = LocalDate.parse(string_date)
            date.format(formatter)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}