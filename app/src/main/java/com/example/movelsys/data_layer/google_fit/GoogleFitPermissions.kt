package com.example.movelsys.data_layer.google_fit

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class GoogleFitPermissions(
    appContext: Context,
    private val activity: Activity
) {
    private val googleFitPermissionsRequestCode = 1
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    private val account = GoogleSignIn.getAccountForExtension(appContext, fitnessOptions)

    @RequiresApi(Build.VERSION_CODES.O)
    fun detectIfPermissionIsGiven() {

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                activity, // your activity
                googleFitPermissionsRequestCode, // e.g. 1
                account,
                fitnessOptions
            )
            Log.i(ContentValues.TAG, "Permission not previously given")
        } else {
            Log.i(ContentValues.TAG, "Permission is given")
        }
    }
}