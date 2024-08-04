package com.somadhan.sopkenenglish.uiFile.otp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somadhan.sopkenenglish.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _resendOtpResult = MutableLiveData<Response<Void>>()
    val resendOtpResult: LiveData<Response<Void>> = _resendOtpResult

    fun resendOtp(email: String?) {
        if (email == null) return
        viewModelScope.launch {
            val response = userRepository.resendOtp(email)
            _resendOtpResult.postValue(response)
        }
    }
}