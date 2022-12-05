package com.example.movelsys.domain_layer.use_cases

import android.text.TextUtils

class ValidateName {
    fun execute(name: String): ValidationResult{
        if (TextUtils.isEmpty(name)) {
            return ValidationResult(false, "This field is obligatory")
        }
        return ValidationResult(true)
    }
}