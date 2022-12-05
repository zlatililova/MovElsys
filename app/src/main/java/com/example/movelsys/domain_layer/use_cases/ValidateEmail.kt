package com.example.movelsys.domain_layer.use_cases

import android.text.TextUtils

class ValidateEmail {

    fun execute(email: String): ValidationResult{
        if (TextUtils.isEmpty(email)){
            return ValidationResult(false, "The email cannot be blank")
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, "The email is not valid")
        }
        println("email true")
        return ValidationResult(true)
    }
}