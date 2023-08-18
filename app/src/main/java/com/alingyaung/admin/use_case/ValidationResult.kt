package com.alingyaung.admin.use_case

data class ValidationResult(
    val success : Boolean,
    val errorMessage: String? = null
)
