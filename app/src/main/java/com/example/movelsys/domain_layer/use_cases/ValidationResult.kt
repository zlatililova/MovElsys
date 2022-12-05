package com.example.movelsys.domain_layer.use_cases

data class ValidationResult(
    val successful: Boolean,
    val errors: String = ""
)
