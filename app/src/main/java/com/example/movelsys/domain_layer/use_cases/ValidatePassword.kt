package com.example.movelsys.domain_layer.use_cases

class ValidatePassword {
    //change the fun name, make errors enum, com
    fun execute(password: String): ValidationResult{
        val minimalPasswordLength = 6
        if(password.length <= minimalPasswordLength){
            return ValidationResult(false, "The password should be longer than 6 characters")
        }
        return ValidationResult(true)
    }

}