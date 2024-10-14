package com.akirachix.totosteps.api

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${getAuthToken()}")
            .build()
        return chain.proceed(request)
    }

    private fun getAuthToken(): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("AUTH_TOKEN", "") ?: ""
    }
}

object ApiClient {
    const val BASE_URL = "https://totosteps-29a482165136.herokuapp.com"

    private var appContext: Context? = null
    private var apiInterface: ApiInterface? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun instance(): ApiInterface {
        if (apiInterface == null) {
            if (appContext == null) {
                throw UninitializedPropertyAccessException("ApiClient must be initialized with a Context before use")
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(appContext!!))
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiInterface = retrofit.create(ApiInterface::class.java)
        }

        return apiInterface!!
    }
}


