package com.somadhan.sopkenenglish.data.network.api

import com.somadhan.sopkenenglish.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface UserApi {
    @POST("/users/initiate-signup")
    suspend fun initiateSignup(@Body user: User): Response<Void>

    @POST("/users/resend-otp")
    suspend fun resendOtp(@Body email: String): Response<Void>
}
