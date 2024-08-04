package com.somadhan.sopkenenglish.data.repository

import com.somadhan.sopkenenglish.data.model.User
import com.somadhan.sopkenenglish.data.network.api.UserApi
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    suspend fun initiateSignup(user: User): Response<Void> {
        return userApi.initiateSignup(user)
    }

    suspend fun resendOtp(email: String): Response<Void> {
        return userApi.resendOtp(email)
    }
}
