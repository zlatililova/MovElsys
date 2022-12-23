package com.example.movelsys.data_layer.google_fit

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

class GoogleFitFetchData(
    val activity: Activity,
    val context: Context
) {
    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    private val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)

    fun subscribeToStepsListener(){
        Fitness.getRecordingClient(activity, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
            // This example shows subscribing to a DataType, across all possible data
            // sources. Alternatively, a specific DataSource can be used.
            .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully subscribed! Step sub")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "There was a problem subscribing. - step sub", e)
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
}