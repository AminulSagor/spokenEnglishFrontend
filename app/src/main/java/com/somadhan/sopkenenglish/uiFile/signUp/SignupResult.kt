package com.somadhan.sopkenenglish.uiFile.signUp

sealed class SignupResult {
    object Idle : SignupResult()
    object Success : SignupResult()
    data class Error(val errorMessage: String) : SignupResult()
}
