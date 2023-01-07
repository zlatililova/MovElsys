package com.example.movelsys.data_layer.google_fit

import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet

interface Responses {
    fun onSuccess()
    fun onError(error: String)
}