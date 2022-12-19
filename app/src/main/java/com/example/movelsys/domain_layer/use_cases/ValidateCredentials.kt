package com.example.movelsys.domain_layer.use_cases

import android.text.TextUtils
import com.example.movelsys.data_layer.authentication.Errors

class ValidateCredentials {

    private val minimalPasswordLength = 6

    fun nameErrorCheck(name: String): Errors? {
        if (TextUtils.isEmpty(name)) {
            return Errors.OBLIGATORY
        }
        return null
    }

    fun emailErrorCheck(email: String): Errors? {
        if (TextUtils.isEmpty(email)) {
            return Errors.OBLIGATORY
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Errors.INVALID_EMAIL
        }
        return null
    }

    fun passwordErrorCheck(password: String): Errors? {
        if (password.length <= minimalPasswordLength) {
            return Errors.INVALID_PASSWORD
        }
        return null
    }

    fun confirmationPasswordErrorCheck(password: String, confirmPassword: String): Errors? {
        if (password != confirmPassword) {
            return Errors.PASSWORDS_NOT_MATCHING
        }
        return null
    }
}