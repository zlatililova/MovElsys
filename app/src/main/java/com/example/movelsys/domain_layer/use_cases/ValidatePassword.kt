package com.example.movelsys.domain_layer.use_cases

class ValidatePassword {
    fun execute(password: String): ValidationResult{
        if(password.length <= 6){
            return ValidationResult(false, "The password should be longer than 6 characters")
        }
        println("password true")

        return ValidationResult(true)
    }
}