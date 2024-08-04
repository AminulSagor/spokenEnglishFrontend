package com.somadhan.sopkenenglish.uiFile.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somadhan.sopkenenglish.data.model.User
import com.somadhan.sopkenenglish.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _signupResult = MutableLiveData<SignupResult>(SignupResult.Idle)
    val signupResult: LiveData<SignupResult> = _signupResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _isTermsAccepted = MutableLiveData<Boolean>()
    val isTermsAccepted: LiveData<Boolean> = _isTermsAccepted

    fun initiateSignup(username: String, email: String, password: String) {
        _isLoading.value = true  // Set loading to true
        val user = User(username, email, password)
        viewModelScope.launch {
            try {
                val response = userRepository.initiateSignup(user)
                if (response.isSuccessful) {
                    _signupResult.postValue(SignupResult.Success)
                } else {
                    _signupResult.postValue(SignupResult.Error(response.errorBody()?.string() ?: "Unknown error"))
                }
            } catch (e: Exception) {
                _signupResult.postValue(SignupResult.Error(e.localizedMessage ?: "Unknown error"))
            } finally {
                _isLoading.postValue(false)  // Reset loading to false
            }
        }
    }

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun onTermsAcceptedChanged(isAccepted: Boolean) {
        _isTermsAccepted.value = isAccepted
    }

    fun resetSignupResult() {
        _signupResult.value = SignupResult.Idle
    }
}
