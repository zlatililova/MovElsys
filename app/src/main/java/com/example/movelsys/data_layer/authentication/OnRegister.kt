package com.example.movelsys.data_layer.authentication

interface OnRegister{
    fun onSuccess()
    fun onError(string: String?)
}