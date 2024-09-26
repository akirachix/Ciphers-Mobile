package com.akirachix.totosteps.api

import com.akirachix.totosteps.models.LoginRequest
import com.akirachix.totosteps.models.LoginResponse
import com.akirachix.totosteps.models.RegistrationResponse
import com.akirachix.totosteps.models.UserRegistration
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("auth/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @POST("api/register/")
    fun registerUser(@Body user: UserRegistration): Call<RegistrationResponse>



}
