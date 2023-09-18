package com.alingyaung.admin.domain.use_case

data class ValidationResult(
    val success : Boolean,
    val errorMessage: String? = null
)
