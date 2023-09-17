package com.alingyaung.admin.extension

import com.alingyaung.admin.use_case.ValidationResult

fun String.validate() = when{
    this.isBlank() ->{
        ValidationResult(
        success = false,
        errorMessage = "Required"
    )}
    else ->{
        ValidationResult(
            success = true,
        )
    }
}

fun Double.validate() = when{
    this.equals(0.0) ->{
        ValidationResult(
            success = false,
            errorMessage = "Required"
        )}
    else ->{
        ValidationResult(
            success = true,
        )
    }
}

fun Int.validate() = when{
    this == 0 ->{
        ValidationResult(
            success = false,
            errorMessage = "Required"
        )}
    else ->{
        ValidationResult(
            success = true,
        )
    }
}
