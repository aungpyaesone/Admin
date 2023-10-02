package com.alingyaung.admin.utils.extension

import com.alingyaung.admin.domain.use_case.ValidationResult
import java.text.DecimalFormat

fun String.validate() = when{
    this.isBlank() ->{
        ValidationResult(
        success = false,
        errorMessage = "Required"
    )
    }
    else ->{
        ValidationResult(
            success = true,
        )
    }
}

fun Double?.validate() = when{
    this == 0.0 ->{
        ValidationResult(
            success = false,
            errorMessage = "Required"
        )
    }
    else ->{
        ValidationResult(
            success = true,
        )
    }
}

fun Int?.validate() = when{
    this == 0 ->{
        ValidationResult(
            success = false,
            errorMessage = "Required"
        )
    }
    else ->{
        ValidationResult(
            success = true,
        )
    }
}

fun Long?.validate() = when{
    this == 0L ->{
        ValidationResult(
            success = false,
            errorMessage = "Required"
        )
    }
    else ->{
        ValidationResult(
            success = true,
        )
    }
}

fun Double?.format(): String = this?.let { DecimalFormat("#,### ကျပ်").format(this)} ?: "0.0"
