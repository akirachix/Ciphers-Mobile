package com.akirachix.totosteps.api

import com.akirachix.totosteps.models.Answer
import com.akirachix.totosteps.models.LoginRequest
import com.akirachix.totosteps.models.LoginResponse
import com.akirachix.totosteps.models.Question
import com.akirachix.totosteps.models.RegistrationResponse
import com.akirachix.totosteps.models.UserRegistration
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @POST("/auth/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @POST("/api/register/")
    fun registerUser(@Body user: UserRegistration): Call<RegistrationResponse>

    @GET("/api/questions/category/{category}/")
    suspend fun getQuestions(@Path("category") category: String): List<Question>





}