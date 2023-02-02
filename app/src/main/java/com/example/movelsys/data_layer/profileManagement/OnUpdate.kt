package com.example.movelsys.data_layer.profileManagement

interface OnUpdate {
    fun onSuccess()
    fun onError(string: String?)
}