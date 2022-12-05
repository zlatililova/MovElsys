package com.example.movelsys.domain_layer.use_cases

class ValidateConfPass {
    fun execute(password: String, confirmPassword: String): ValidationResult{
        if(password != confirmPassword){
            return ValidationResult(false, "The passwords are not matching")
        }
        return ValidationResult(true)
    }
}