
package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.api.ApiInterface
import com.akirachix.totosteps.databinding.ActivityLoginBinding
import com.akirachix.totosteps.models.LoginRequest
import com.akirachix.totosteps.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import org.json.JSONObject
import android.util.Log
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRetrofit() // Initialize Retrofit and API service
        setupListeners() // Set up button listeners
    }

    // Initialize Retrofit
    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://totosteps-29a482165136.herokuapp.com/") // Replace with your actual API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiInterface::class.java)
    }

    // Set up button listeners
    private fun setupListeners() {
        // Handle Sign Up button click
        binding.btnSignUp.setOnClickListener {
            if (validateInputs()) {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString()
                loginUser(email, password) // Call login function
            }
        }

        // Navigate to SignupActivity when clicking "Already have an account?"
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,HomeScreenActivity::class.java))
        }
    }

    // Validate login inputs
    private fun validateInputs(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // Login function
    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.success == true) {
                        // Successful login
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()

                        // Navigate to HomeScreenActivity after successful login

                        finish()
                    } else {
                        // Show invalid credentials message
                        Toast.makeText(this@LoginActivity, loginResponse?.message ?: "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle errors like incorrect credentials or server errors
                    try {
                        val errorBody = response.errorBody()?.string()
                        val jsonObject = JSONObject(errorBody)
                        val errorMessage = jsonObject.getString("message")
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@LoginActivity, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("LoginActivity", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle network or connection errors
                Toast.makeText(this@LoginActivity, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Network error", t)
            }
        })
    }

}