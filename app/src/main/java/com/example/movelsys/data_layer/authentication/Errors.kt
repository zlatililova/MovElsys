package com.example.movelsys.data_layer.authentication

enum class Errors(val Message: String) {
    INVALID_CREDENTIALS("The credentials you entered are invalid"),
    OBLIGATORY("This field is obligatory"),
    INVALID_EMAIL("The email is not valid"),
    INVALID_PASSWORD("The password should be longer than 6 characters"),
    PASSWORDS_NOT_MATCHING("The passwords are not matching"),
}