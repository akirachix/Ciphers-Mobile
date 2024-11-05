package com.akirachix.totosteps.api

import com.akirachix.totosteps.models.AutismResultResponse
import com.akirachix.totosteps.models.ImageUpload
import com.akirachix.totosteps.models.ChildData
import com.akirachix.totosteps.models.ChildResponse
import com.akirachix.totosteps.models.LoginRequest
import com.akirachix.totosteps.models.LoginResponse
import com.akirachix.totosteps.models.Milestone
import com.akirachix.totosteps.models.ParentResponse
import com.akirachix.totosteps.models.Question
import com.akirachix.totosteps.models.RegistrationResponse
import com.akirachix.totosteps.models.ResultData
import com.akirachix.totosteps.models.ResultResponse
import com.akirachix.totosteps.models.UserRegistration
import com.akirachix.totosteps.resources
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiInterface {

    @POST("/auth/login/")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/register/")
    fun registerUser(@Body user: UserRegistration): Call<RegistrationResponse>

    @GET("/api/questions/category/{category}/")
    suspend fun getQuestions(@Path("category") category: String): List<Question>

    @POST("/api/children/")
    suspend fun createChild(@Body childData: ChildData): Response<ChildResponse>

    @GET("api/parent/{parentId}/")
    suspend fun getParentData(@Path("parentId") parentId: Int): ParentResponse

    @GET("api/milestones/")
    fun getMilestones(): Call<List<Milestone>>

    @GET("/api/resources")
    suspend fun getResources(): Response<List<resources>>

    @POST("/api/result/")
    fun submitResult(@Body result: ResultData): Call<ResultResponse>

    @Multipart
    @POST("/api/upload/")
    suspend fun uploadPhoto(
        @Part image: MultipartBody.Part,
        @Part("child") child: RequestBody
    ): Response<AutismResultResponse>


    @POST("api/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResponseBody>

}

data class ResetPasswordRequest(
    val email: String,
    val password: String
)

data class ResetPasswordResponse(
    val message: String,
    val success: Boolean
)





