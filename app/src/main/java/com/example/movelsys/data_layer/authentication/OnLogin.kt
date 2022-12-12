package com.example.movelsys.data_layer.authentication

interface OnLogin {
    fun onSuccess()
    fun onError(string: String?)
}