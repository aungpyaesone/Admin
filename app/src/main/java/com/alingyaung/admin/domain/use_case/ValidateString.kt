package com.alingyaung.admin.domain.use_case

class ValidateString{
    fun execute(name:String): ValidationResult {
        if(name.isBlank()){
            return ValidationResult(
                success = false,
                errorMessage = "required"
            )
        }
        return ValidationResult(
            success = true
        )
    }
}
