package com.somadhan.sopkenenglish.utils

fun validateUsername(username: String): String? {
    val usernamePattern = "^[a-zA-Z ]{3,15}$"
    return when {
        username.isBlank() -> "Username cannot be empty"
        !username.matches(usernamePattern.toRegex()) -> "Username must be 3-15 characters long and contain only letters"
        else -> null
    }
}

fun validateEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email cannot be empty"
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
        else -> null
    }
}
fun isEmailAlreadyExistsError(errorMessage: String?): Boolean {
    return errorMessage?.contains("Email already exists") == true
}

fun validatePassword(password: String): String? {
    return when {
        password.isBlank() -> "Password cannot be empty"
        password.length < 6 -> "Password must be at least 6 characters long"
        else -> null
    }
}

fun validateConfirmPassword(password: String, confirmPassword: String): String? {
    return when {
        confirmPassword.isBlank() -> "Confirm Password cannot be empty"
        password != confirmPassword -> "Passwords do not match"
        else -> null
    }
}